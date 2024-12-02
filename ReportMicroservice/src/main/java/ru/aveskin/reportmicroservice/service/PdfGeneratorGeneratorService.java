package ru.aveskin.reportmicroservice.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.aveskin.reportmicroservice.dto.ReportRequestDto;
import ru.aveskin.reportmicroservice.entity.MarketAction;
import ru.aveskin.reportmicroservice.entity.ReportContent;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PdfGeneratorGeneratorService implements ReportGeneratorService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public byte[] generate(ReportRequestDto request) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            log.info("Попытка создания PDF");
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument, PageSize.A4);

            String fontPath = "C:/Windows/Fonts/arial.ttf";
            PdfFont font = PdfFontFactory.createFont(fontPath);

            // Заголовок документа
            document.add(new Paragraph("Отчет по покупкам/продажам за период: с "
                    + request.getDateLow() + " по " + request.getDateHigh()).setFont(font).setFontSize(20));

            log.info("Создание таблицы с заголовками");
            Table table = new Table(6);


            table.addHeaderCell("Portfolio ID");
            table.addHeaderCell("Ticker");
            table.addHeaderCell("Name");
            table.addHeaderCell("Quantity");
            table.addHeaderCell("Current Price");
            table.addHeaderCell("Created At");

            log.info("получение данных");
            List<ReportContent> content = getContentByTickerAndDateRange(request.getTicker(),
                    request.getDateLow(),
                    request.getDateHigh());

            log.info("Добавление данных в таблицу");
            for (ReportContent entity : content) {
                table.addCell(entity.getPortfolioId().toString());
                table.addCell(entity.getTicker());
                table.addCell(entity.getName()).setFont(font);
                table.addCell(entity.getQuantity().toString());
                table.addCell(entity.getCurrentPrice() != null ? entity.getCurrentPrice().toString() : "N/A");
                table.addCell(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : "N/A");
            }

            log.info("Добавление таблицы в документ");
            document.add(table);
            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Ошибка при создании PDF-документа", e);
        }

    }

    private List<ReportContent> getContentByTickerAndDateRange(String ticker, LocalDate dateLow, LocalDate dateHigh) {
        String sql = "SELECT * FROM portfolio.action_history WHERE ticker = ? AND created_at BETWEEN ? AND ?";

        log.info("Выполнение запроса в БД");
        return jdbcTemplate.query(sql, new Object[]{
                ticker,
                dateLow,
                dateHigh
        }, reportContentRowMapper);
    }

    private final RowMapper<ReportContent> reportContentRowMapper = (rs, rowNum) -> {
        ReportContent reportContent = new ReportContent();
        reportContent.setPortfolioId(rs.getLong("portfolio_id"));
        reportContent.setTicker(rs.getString("ticker"));
        reportContent.setName(rs.getString("name"));
        reportContent.setQuantity(rs.getInt("quantity"));
        reportContent.setCurrentPrice(rs.getBigDecimal("current_price"));
        reportContent.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        int actionValue = rs.getInt("action");
        if (actionValue == 0) {
            reportContent.setAction(MarketAction.BUY);
        } else {
            reportContent.setAction(MarketAction.SELL);
        }

        return reportContent;
    };
}
