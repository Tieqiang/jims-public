package com.jims.wx.facade;

import com.jims.wx.BaseFacade;
import com.jims.wx.vo.PatVisitVo;

import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wei on 2016/4/22.
 */
public class PatVisitFacade extends BaseFacade {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public List<PatVisitVo> listPatVisitVo(String patientIds) {
        try {
            String sql = "select a.patient_id as patientId,\n" +
                    "      a.visit_id as visitId,\n" +
                    "      a.next_of_kin as nextOfKin,\n" +
                    "       b.dept_name deptAdmissionTo ,\n" +
                    "       a.admission_date_time as admissionDateTime ,\n" +
                    "       c.dept_name deptDischargeFrom ,\n" +
                    "       a.discharge_date_time as dischargeDateTime\n" +
                    "  from wx.pat_visit a, wx.dept_dict_view b, wx.dept_dict_view c\n" +
                    " where a.dept_admission_to = b.dept_code\n" +
                    "   and a.dept_discharge_from = c.dept_code(+)" +
                    "   and a.patient_id  in (" + patientIds + ") ";
            List<PatVisitVo> patVisitVos = new ArrayList<>();
            Query qu = createNativeQuery(sql);
            List<Objects[]> resultList = qu.getResultList();
            for (Object[] objects : resultList) {
                PatVisitVo patVisitVo = new PatVisitVo(objects[0] == null ? null : objects[0].toString(), Double.parseDouble(objects[1].toString()), objects[3].toString(), objects[4].toString(), objects[5] == null ? null : objects[5].toString(), objects[6] == null ? null : objects[6].toString(), objects[2] == null ? null : objects[2].toString());

                if (objects[4] != null) {

                    patVisitVo.setAdmissionDateTime(sdf.format(sdf.parse(patVisitVo.getAdmissionDateTime().substring(0, 19))));
                }
                if (objects[6] != null) {
                    patVisitVo.setDischargeDateTime(sdf.format(sdf.parse(patVisitVo.getDischargeDateTime().substring(0, 19))));
                }

                patVisitVos.add(patVisitVo);
            }
            return patVisitVos;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
