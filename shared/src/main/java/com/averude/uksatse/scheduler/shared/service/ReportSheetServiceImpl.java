package com.averude.uksatse.scheduler.shared.service;

import com.averude.uksatse.scheduler.core.model.dto.ReportSheetDTO;
import com.averude.uksatse.scheduler.core.model.entity.ReportSheet;
import com.averude.uksatse.scheduler.core.model.entity.ReportSheetShift;
import com.averude.uksatse.scheduler.shared.repository.ReportSheetRepository;
import com.averude.uksatse.scheduler.shared.repository.ReportSheetShiftRepository;
import com.averude.uksatse.scheduler.shared.service.base.AService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportSheetServiceImpl
        extends AService<ReportSheet, Long>
        implements ReportSheetService {

    private final ReportSheetRepository reportSheetRepository;
    private final ReportSheetShiftRepository reportSheetShiftRepository;

    public ReportSheetServiceImpl(ReportSheetRepository reportSheetRepository,
                                  ReportSheetShiftRepository reportSheetShiftRepository) {
        super(reportSheetRepository);
        this.reportSheetRepository = reportSheetRepository;
        this.reportSheetShiftRepository = reportSheetShiftRepository;
    }

    @Override
    @Transactional
    public List<ReportSheetDTO> findAllByDepartmentId(Long departmentId) {
        var reportSheets = reportSheetRepository.findAllByDepartmentId(departmentId);
        return reportSheets.stream()
                .map(subDepartment ->
                        new ReportSheetDTO(subDepartment, getIds(subDepartment.getReportSheetShifts())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReportSheetDTO save(ReportSheetDTO dto) {
        var reportSheet = dto.getReportSheet();
        if (reportSheet.getId() == null) {
            reportSheetRepository.save(reportSheet);
        }

        var shiftIds = dto.getShiftIds();
        reportSheetShiftRepository.deleteAllByReportSheetIdAndShiftIdNotIn(reportSheet.getId(), shiftIds);

        var reportSheetShifts = shiftIds.stream()
                .map(shiftId -> new ReportSheetShift(reportSheet.getId(), shiftId))
                .collect(Collectors.toList());

        reportSheet.setReportSheetShifts(reportSheetShifts);
        reportSheetRepository.save(reportSheet);
        return dto;
    }

    private List<Long> getIds(List<ReportSheetShift> hasIds) {
        return hasIds.stream().map(ReportSheetShift::getShiftId).collect(Collectors.toList());
    }
}

