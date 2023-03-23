package org.octri.omop_annotator.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.config.OmopDataConfiguration;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.view.ExportedJudgmentRow;
import org.octri.omop_annotator.view.OptionList;
import org.octri.omop_annotator.view.PoolSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * Data Export controller
 */
@Controller
@RequestMapping("/admin/export")
public class DataExportController {

    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private JudgmentRepository judgmentRepository;
    @Autowired
    private CustomViewRepository customViewRepository;

    @Autowired
    private OmopDataConfiguration dataConfig;

    /**
     * User interface for choosing the parameters to export judgments.
     *
     * @param model
     * @return
     */
    @GetMapping("judgments")
    public String exportJudgmentsForm(Map<String, Object> model) {
        model.put("poolOptions", OptionList.fromSearch(poolRepository.findAll(), null));
        return "export/judgments";
    }

    /**
     * Generates and streams a CSV file of judgments for a given pool and user.
     */
    @PostMapping(path = "judgments")
    public void exportJudgmentsCsv(HttpServletResponse response, @Valid @ModelAttribute JudgmentExportParams params)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

        List<ExportedJudgmentRow> results = judgmentRepository
                .findAllByPoolEntryPoolId(params.getPoolId())
                .stream()
                .map(judgment -> ExportedJudgmentRow.fromJudgment(dataConfig.getRefreshDate(), judgment))
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        String filename = "judgments_pool_" + params.getPoolId() + "_"
                + today.toString() + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        var writer = new CSVWriter(response.getWriter());
        writer.writeNext(ExportedJudgmentRow.FIELDS);
        for (ExportedJudgmentRow row : results) {
            writer.writeNext(row.toCsvRow());
        }
        writer.close();
    }

    /**
     * Generates and streams a CSV file summarizing the admin dashboard data for the pool
     * 
     * @param response
     * @param poolId
     * @throws IOException
     */
    @GetMapping(path = "dashboard/{poolId}")
    public void exportDashboardCsv(HttpServletResponse response, @PathVariable Long poolId)
            throws IOException {
        List<PoolSummary> results = customViewRepository.summarizePool(poolId);
        var writer = new CSVWriter(response.getWriter());
        writer.writeNext(PoolSummary.CSV_FIELDS);
        for (var row : results) {
            writer.writeNext(row.toCsvRow());
        }
        writer.close();
    }

    class JudgmentExportParams {

        @NotNull
        private Long poolId;

        public Long getPoolId() {
            return poolId;
        }

        public void setPoolId(Long poolId) {
            this.poolId = poolId;
        }

    }
}
