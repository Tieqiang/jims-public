package com.jims.wx.facade;

import com.jims.wx.BaseFacade;
import com.jims.wx.entity.HospitalDict;
import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.entity.WxOpenAccountConfig;
import com.jims.wx.vo.AppSetVo;
import com.jims.wx.vo.InpBillDetailVo;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wei on 2016/4/22.
 */
public class InpBillDetailFacade extends BaseFacade {

    public List<InpBillDetailVo> listInpBillVo(String patientId, double visitId) {
        String sql = "select  a.patient_id as patientId,a.visit_id as visitId,a.item_name as itemName ," +
                "a.amount as amount, a.units as units , a.costs as costs,a.charges as charges" +
                " from INPBILL.inp_bill_detail a where patient_id ='" + patientId + "' and visit_id='" + visitId + "'";
        List<InpBillDetailVo> inpBillDetailVos = new ArrayList<>();
        Query qu = createNativeQuery(sql);
        List<Objects[]> resultList = qu.getResultList();
        for (Object[] objects : resultList) {
            InpBillDetailVo inpBillDetailVo = new InpBillDetailVo(objects[0].toString(), Double.parseDouble(objects[1].toString()), objects[2].toString(), Double.parseDouble(objects[3].toString()), objects[4].toString(), Double.parseDouble(objects[5].toString()), Double.parseDouble(objects[6].toString()));
            inpBillDetailVos.add(inpBillDetailVo);
        }
        return inpBillDetailVos;
    }


}
