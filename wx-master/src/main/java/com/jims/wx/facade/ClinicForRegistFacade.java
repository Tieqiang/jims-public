package com.jims.wx.facade;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicForRegist;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.entity.ClinicSchedule;
import com.jims.wx.entity.ClinicTypeCharge;
import com.jims.wx.util.StringUtils;
import com.jims.wx.vo.ClinicForRegistVO;
import com.jims.wx.vo.DayOfWeek;
import com.jims.wx.vo.TimeOfDay;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cxy on 2016/3/21.
 */
public class ClinicForRegistFacade extends BaseFacade {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private EntityManager entityManager;
    private ClinicIndexFacade clinicIndexFacade;
    private ClinicTypeChargeFacade clinicTypeChargeFacade;
    private ClinicScheduleFacade clinicScheduleFacade;
//    private ThreadLocal<Integer> shareData = new ThreadLocal<Integer>();

    @Inject
    public ClinicForRegistFacade(EntityManager entityManager, ClinicIndexFacade clinicIndexFacade, ClinicTypeChargeFacade clinicTypeChargeFacade, ClinicScheduleFacade clinicScheduleFacade) {
        this.entityManager = entityManager;
        this.clinicIndexFacade = clinicIndexFacade;
        this.clinicTypeChargeFacade = clinicTypeChargeFacade;
        this.clinicScheduleFacade = clinicScheduleFacade;
    }

    /**
     * 保存对象内容
     *
     * @param saveData
     * @return
     */
    @Transactional
    public ClinicForRegist save(ClinicForRegist saveData) {
        return merge(saveData);
    }

    /**
     * 修改对象内容
     *
     * @param updateData
     * @return
     */
    @Transactional
    public List<ClinicForRegist> update(List<ClinicForRegist> updateData) {
        List<ClinicForRegist> newUpdateDict = new ArrayList<>();
        if (updateData != null && updateData.size() > 0) {
            for (ClinicForRegist obj : updateData) {
                ClinicForRegist merge = merge(obj);
                newUpdateDict.add(merge);
            }
        }
        return newUpdateDict;
    }

    /**
     * 批量删除
     *
     * @param clazz
     * @param ids
     * @return
     */
    @Transactional
    public List<String> delete(Class<ClinicForRegist> clazz, String ids) {
        List<String> list = new ArrayList<String>();
        String[] idStr = ids.split(",");
        for (int j = 0; j < idStr.length; j++) {
            list.add(idStr[j]);
        }
        super.removeByStringIds(clazz, list);
        return list;
    }

    /**
     * 判断是否能生成号表
     *
     * @param date
     * @param clinicIndexId
     * @return
     */
    public Map<String, Object> judgeIsRegist(String date, String clinicIndexId, String date1, Integer flag) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "from ClinicSchedule where dayOfWeek between '" + sdf.format(sdf.parse(date)) + "' and '" + sdf.format(sdf.parse(date1)) + "' and clinicIndex.id='" + clinicIndexId + "'";
        List<ClinicSchedule> list = entityManager.createQuery(sql).getResultList();
        if (list == null) {
            map.put("isRegist", false);
            map.put("msg", "当前日期不能生成号表!");
        } else {
            map.put("isRegist", true);
            map.put("msg", "号表可以生成！");
            if (flag != 1) {
                map.put("list", list);
            }
        }
        return map;
    }

    /**
     * @param date
     * @param clinicIndexId
     * @return
     * @author chenxiaoyang
     * @description 号表生成
     */
    public Map<String, Object> registTable(String date, String clinicIndexId, String date1, String desc, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ClinicForRegist> list = new ArrayList<ClinicForRegist>();
        try {
            List<Object[]> dayTimes = clinicScheduleFacade.queryDayAndTime(clinicIndexId);
            for (int i = 0; i < dayTimes.size(); i++) {
//                System.out.println("可以出诊的时间为：" + dayTimes.get(i)[0].toString() + dayTimes.get(i)[1].toString());
                List<String> registdateList = getNumbersOfWeekJ(date, date1, dayTimes.get(i)[0].toString(), dayTimes.get(i)[1].toString());
                for (int j = 0; j < registdateList.size(); j++) {
                    //判断这个号别 这个时间 是否已经生成了号表
                    boolean flag = judgeIsExists(clinicIndexId, registdateList.get(j));//
                    if (!flag) {
                        ClinicForRegist clinicForRegist = saveRecord(dayTimes.get(i)[2].toString(), new Date(), clinicIndexFacade.findById(clinicIndexId), registdateList.get(j), registdateList.get(j) + " " + dayTimes.get(i)[0].toString() + " " + dayTimes.get(i)[1].toString());
                        list.add(clinicForRegist);
                    }
                }
            }
            if (list != null && !list.isEmpty()) {
                map.put("isRegist", true);
            } else {
                map.put("isRegist", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isRegist", false);
        }
        return map;
    }

    /**
     * 判断某个号别 某个时间的号表是否存在
     *
     * @param clinicIndexId
     * @param date
     * @return if 存在 true else false
     */
    private boolean judgeIsExists(String clinicIndexId, String date) {
        String hql = "from ClinicForRegist where clinicIndex.id='" + clinicIndexId + "'and timeDesc like '%" + date + "%'";
//        System.out.println("hql="+hql);
        List<ClinicForRegist> list = entityManager.createQuery(hql).getResultList();
        if (list != null && !list.isEmpty())
            return true;
        return false;
    }

    /**
     * @param parse clinicDate
     * @param byId  号别
     * @param desc  描述
     * @author chenxiaaoyang
     * @Description保存号表记录
     */
    private ClinicForRegist saveRecord(String limits, Date parse, ClinicIndex byId, String registTime, String desc) {
        ClinicForRegist c = new ClinicForRegist();
        c.setClinicDate(parse);
        c.setClinicIndex(byId);
//        String limits = clinicScheduleFacade.findLimitsByClinicIndexId(byId.getId()).toString();
        if (limits.contains(".")) {
            limits = limits.substring(0, limits.indexOf("."));
        }
        c.setRegistrationLimits(Integer.parseInt(limits));
        c.setRegistrationNum(0);
        c.setAppointmentLimits(Integer.parseInt(limits));
//        Integer preNo = countHaveRegistedRecord(byId.getId()).intValue();
        c.setCurrentNo(0);
        c.setTimeDesc(desc);
        Double price = clinicTypeChargeFacade.findPriceByClinicTypeSettingId(byId.getClinicTypeId());
        c.setRegistPrice(price);
        c.setRegistTime(registTime);
        ClinicForRegist clinicForRegist = save(c);
        return clinicForRegist;
    }

    /**
     * @param id
     * @return
     * @description 通过号别id统计已经生成的号表
     */
    private Long countHaveRegistedRecord(String id) {
        return (Long) entityManager.createQuery("select count(*) from ClinicForRegist where clinicIndex.id='" + id + "'").getSingleResult();
    }

    /**
     * @param startTime yyyy-MM-dd HH:mm:ss
     * @param endTime   yyyy-MM-dd HH:mm:ss
     * @param dayOfWeek 星期(1/2/3...)
     * @param timeOfDay 上午/下午/晚上
     * @return Map
     * @description 通过 startTime 和endTime 找出 有几个 星期一的上午
     * 并且把哪天有星期一上午的日期记录下来 加入list集合。
     * @author chenxiaoyang
     */
    private List<String> getNumbersOfWeekJ(String startTime, String endTime, String dayOfWeek, String timeOfDay) {
        List<String> list = findCountDayOfWeek(startTime, endTime, dayOfWeek);
        try {
            int dayForWeek = dayForWeek(startTime);
            String dow = revert(dayForWeek);
            if (dow.equals(dayOfWeek)) {
                //出诊安排在上午 你选择的是12点之后
                if (timeOfDay.equals(TimeOfDay.上午.toString()) && Integer.parseInt(startTime.substring(12, 14)) > 12) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                } else if (timeOfDay.equals(TimeOfDay.下午.toString()) && Integer.parseInt(startTime.substring(12, 14)) > 18) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                } else if (timeOfDay.equals(TimeOfDay.晚上.toString()) && Integer.parseInt(startTime.substring(12, 14)) > 22) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                }
            }
            int dayForWeek2 = dayForWeek(endTime);
            String dow2 = revert(dayForWeek2);
            if (dow.equals(dayOfWeek)) {
                if (timeOfDay.equals(TimeOfDay.上午.toString()) && Integer.parseInt(startTime.substring(12, 14)) < 8) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                } else if (timeOfDay.equals(TimeOfDay.下午.toString()) && Integer.parseInt(startTime.substring(12, 14)) < 14) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                } else if (timeOfDay.equals(TimeOfDay.晚上.toString()) && Integer.parseInt(startTime.substring(12, 14)) < 18) {
                    list.remove(sdf.format(sdf.parse(startTime)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param dayForWeek
     * @return
     * @description 装换成字符串
     */
    private String revert(int dayForWeek) {
        if (dayForWeek == 1) {
            return DayOfWeek.星期一.toString();
        } else if (dayForWeek == 2) {
            return DayOfWeek.星期二.toString();
        } else if (dayForWeek == 3) {
            return DayOfWeek.星期三.toString();
        } else if (dayForWeek == 4) {
            return DayOfWeek.星期四.toString();
        } else if (dayForWeek == 5) {
            return DayOfWeek.星期五.toString();
        } else if (dayForWeek == 6) {
            return DayOfWeek.星期六.toString();
        } else {
            return DayOfWeek.星期日.toString();
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * @param dateFrom 开始时间  yyyy-MM-dd HH:mm:ss
     * @param dateEnd  结束时间   yyyy-MM-dd HH:mm:ss
     * @param weekDays 星期
     * @return 返回时间list
     * @author chenxiaoyang
     * @description 获取某一时间段特定星期几的日期
     */
    private List<String> findCountDayOfWeek(String dateFrom, String dateEnd, String weekDays) {
        long time = 1l;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strWeekNumber = weekForNum(weekDays);
        try {
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
            while (true) {
                time = sdf.parse(dateFrom).getTime();
                time = time + perDayMilSec;
                Date date = new Date(time);
                dateFrom = sdf.format(date);
                if (dateFrom.compareTo(dateEnd) <= 0) {
                    Integer weekDay = dayForWeek(date);
                    if (strWeekNumber.indexOf(weekDay.toString()) != -1) {
                        System.out.println(dateFrom);
                        dateList.add(dateFrom);
                    }
                } else {
                    break;
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return dateList;
    }

    /**
     * 判断一个日期周几
     *
     * @param date
     * @return
     */
    public static Integer dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前是低级周
     *
     * @param weekDays
     * @return
     */
    public static String weekForNum(String weekDays) {
        String weekNumber = "";
        if (weekDays.indexOf("|") != -1) {//多个星期数
            String[] strWeeks = weekDays.split("\\|");
            for (int i = 0; i < strWeeks.length; i++) {
                weekNumber = weekNumber + "" + getWeekNum(strWeeks[i]).toString();
            }
        } else {//一个星期数
            weekNumber = getWeekNum(weekDays).toString();
        }
        return weekNumber;
    }

    /**
     * 字符串与整型互相转换
     *
     * @param strWeek
     * @return
     */
    public static Integer getWeekNum(String strWeek) {
        Integer number = 1;//默认为星期日
        if ("星期日".equals(strWeek)) {
            number = 1;
        } else if ("星期一".equals(strWeek)) {
            number = 2;
        } else if ("星期二".equals(strWeek)) {
            number = 3;
        } else if ("星期三".equals(strWeek)) {
            number = 4;
        } else if ("星期四".equals(strWeek)) {
            number = 5;
        } else if ("星期五".equals(strWeek)) {
            number = 6;
        } else if ("星期六".equals(strWeek)) {
            number = 7;
        } else {
            number = 1;
        }
        return number;
    }

    /**
     * 根据id 查询号表对象
     *
     * @param id
     * @return
     */
    public ClinicForRegist findById(String id) {
        return (ClinicForRegist) entityManager.createQuery("from  ClinicForRegist where id='" + id + "'").getSingleResult();
    }

//    /**
//     * 查找当前挂号人数
//     *
//     * @param dayOfWeek
//     * @return
//     */
//    private Long findcurrentGuaHaoPs(String dayOfWeek) {
//        String sql = null;
//        try {
//            sql = "select count(*) from ClinicForRegist where clinicDate like '%" + dayOfWeek + "%'";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Long count = (Long) entityManager.createQuery(sql).getSingleResult();
//        return count;
//    }
    /**
     //     * 查找当前挂号人数
     //     *
     //     * @param dayOfWeek
     //     * @return
     //     */
//    private Long findcurrentGuaHaoPs(String dayOfWeek) {
//        String sql = null;
//        try {
//            sql = "select count(*) from ClinicForRegist where clinicDate like '%" + dayOfWeek + "%'";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Long count = (Long) entityManager.createQuery(sql).getSingleResult();
//        return count;
//    }

//    /**
//     * @param id
//     * @param flag
//     * @return
//     */
//    private ClinicForRegist findByclinicIndexIdAndDate(String id, int flag) {
//        List<ClinicForRegist> list = new ArrayList<ClinicForRegist>();
//        try {
//            list = entityManager.createQuery("from ClinicForRegist c where c.id='" + id + "'").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list.get(0);
//    }


    /**
     * @param id
     * @return
     * @description 查询号表的详细信息
     */
    public ClinicForRegistVO findInfo(String id) {
        ClinicForRegistVO c = new ClinicForRegistVO();
        ClinicForRegist cc = (ClinicForRegist) entityManager.createQuery("from ClinicForRegist where id='" + id + "'").getSingleResult();
        c.setRegistTime(sdf.format(cc.getClinicDate()));
        c.setAppointmentLimits(cc.getAppointmentLimits() == null ? 0 : cc.getAppointmentLimits());
        c.setRegistrationLimits(cc.getRegistrationLimits());
        c.setClinicDept(cc.getClinicIndex().getClinicDept());
        c.setClinicLabel(cc.getClinicIndex().getClinicLabel());
        c.setCurrentRegistCount(cc.getRegistrationNum());
        c.setCurrentRegistNo(cc.getCurrentNo());
        c.setDescription(cc.getTimeDesc());
        return c;
    }

//    /**
//     * 判断号表约束是否存在
//     *
//     * @param clinicTypeId
//     * @param clinicDate
//     * @param i
//     * @return
//     */
//    public Boolean isExists(String clinicTypeId, String clinicDate, int i) throws Exception {
//        String hql = "from ClinicForRegist c where c.clinicDate like '%" + clinicDate + "%'and c.clinicIndex.id='" + clinicTypeId + "'";
//        List<ClinicIndex> list = entityManager.createQuery
//                (hql)
//                .getResultList();
//        if (!list.isEmpty()) {
//            return false;
//        }
//        return true;
//    }

    /**
     * 条件查询
     *
     * @param clinicForRegistClass
     * @return
     */
    public List<ClinicForRegist> findAllData(Class<ClinicForRegist> clinicForRegistClass, String clinicIndexName, String date) {
        String hql = "from ClinicForRegist where 1=1 ";
        if (clinicIndexName != null && !"".equals(clinicIndexName)) {
            hql += " and timeDesc like '%" + clinicIndexName + "%'";
        }
        if (date != null && !"".equals(date)) {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
                String likeDate = sd.format(sdf.parse(date));
                hql += " and clinicDate like'%" + likeDate + "%'";
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hql += " order by currentNo asc";
        }
        List<ClinicForRegist> list = entityManager.createQuery(hql).getResultList();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                ClinicIndex clinicIndex = clinicIndexFacade.findById(list.get(i).getClinicIndex().getId());
                ClinicIndex c = new ClinicIndex();
                c.setId(clinicIndex.getId());
                c.setClinicDept(clinicIndex.getClinicDept());
                c.setClinicLabel(clinicIndex.getClinicLabel());
                list.get(i).setClinicIndex(c);
            }
        }
        return list;
    }

    /**
     * 查找号别新消息
     *
     * @return
     */
    public List<ClinicIndex> findClinicIndexAll() {
        return entityManager.createQuery("select DISTINCT c.clinicIndex from ClinicForRegist as c ").getResultList();
    }

    /*
     * @description * 如果这个医生当天有出诊就显示当天 如果没有就显示里今天最近的一天
     * @param  clinicIndexId 号别id
     * @param currentDateStr
     * @return
     */
    public ClinicForRegist findRegistInfo(String currentDateStr, String clinicIndexId) {
        List<ClinicForRegist> clinicForRegists = new ArrayList<ClinicForRegist>();
        ClinicForRegist clinicForRegist = null;
        String sql = "from ClinicForRegist where timeDesc like '%" + currentDateStr + "%' and clinicIndex.id='" + clinicIndexId + "'";
        clinicForRegists = entityManager.createQuery(sql).getResultList();
        if (clinicForRegists.isEmpty()) {
            //预约
//            return findRegistInfo(getNextDayStr(currentDateStr), clinicIndexId);
            return null;
        } else {
            return clinicForRegists.get(0);
        }
    }

    /**
     * @param dateStr
     * @return
     */
    public String getNextDayStr(String dateStr) {
        String nextDayStr = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(dateStr));
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            Date nextDay = new Date(calendar.getTimeInMillis());
            nextDayStr = sdf.format(nextDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDayStr;
    }

    /**
     * 查找可以预约的号表
     * 从明天开始计算
     *
     * @param currentDateStr
     * @param clinicIndexId
     * @return
     */
    public List<ClinicForRegist> findRegistInfoPre(String currentDateStr, String clinicIndexId) {
        String nextDayStr = getNextDayStr(currentDateStr);
        List<ClinicForRegist> clinicForRegists = new ArrayList<ClinicForRegist>();
        clinicForRegists = entityManager.createQuery("from ClinicForRegist where registTime>'" + currentDateStr + "'  and registTime < '" + nextDayStr + "' and clinicIndex.id='" + clinicIndexId + "' order by registTime asc").getResultList();
        if (!clinicForRegists.isEmpty())
            return clinicForRegists;
        return null;
    }

    /**
     * @param date
     * @param clinicIndexId
     * @return
     */
    public List<ClinicForRegist> findRegistInfoCollection(String date, String clinicIndexId) {
        List<ClinicForRegist> clinicForRegists = new ArrayList<ClinicForRegist>();

        clinicForRegists = entityManager.createQuery("from ClinicForRegist where registTime>='" + date + "' and   registTime <'" + getNextDayStr(date) + "' and clinicIndex.id='" + clinicIndexId + "' order by registTime asc").getResultList();
        if (!clinicForRegists.isEmpty())
            return clinicForRegists;
        return null;
    }
}
