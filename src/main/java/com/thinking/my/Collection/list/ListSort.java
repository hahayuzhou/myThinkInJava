package com.thinking.my.Collection.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liyong
 * @Date 2021/6/25 5:41 下午
 **/
public class ListSort {


    public static void main(String[] args) {
//        List<List<String>> tlist = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            List<String> list = new ArrayList<>();
//            for(int j=0;j<5;j++){
//                list.add(j+i+"");
//            }
//            tlist.add(list);
//        }
//        System.out.println(tlist);
//
////        tlist = tlist.stream().sorted(Comparator.comparing(e->e.get(3))).collect(Collectors.toList());
////        System.out.println(tlist);
//        tlist = tlist.stream().sorted(Comparator.comparing((List<String> l)-> l.get(2)).reversed()).collect(Collectors.toList());
//        System.out.println(tlist);

        String sss = "doris_common_aiot_data_doris\n" +
                "\n" +
                "doris_common_app_hmsc\n" +
                "\n" +
                "doris_common_ba_rc_doris_prophet\n" +
                "\n" +
                "doris_common_bagualu_doris\n" +
                "\n" +
                "doris_common_consec_webids\n" +
                "\n" +
                "doris_common_dianpingpipeline\n" +
                "\n" +
                "doris_common_doris_mapc\n" +
                "\n" +
                "doris_common_dp_marketing\n" +
                "\n" +
                "doris_common_grocery_predict\n" +
                "\n" +
                "doris_common_mall_ohi_sentinel\n" +
                "\n" +
                "doris_common_mall_wai\n" +
                "\n" +
                "doris_common_thh_uwms_schedule_realtime_log\n" +
                "\n" +
                "doris_common_wolf\n" +
                "\n" +
                "doris_default_cluster_b2b_rt_data\n" +
                "\n" +
                "doris_default_cluster_doris_csp_app\n" +
                "\n" +
                "doris_default_cluster_doris_pep\n" +
                "\n" +
                "doris_default_cluster_fsp_rtdw\n" +
                "\n" +
                "doris_default_cluster_giant_rt\n" +
                "\n" +
                "doris_default_cluster_giant_rt_backup\n" +
                "\n" +
                "doris_default_cluster_grocery_doris_usr_analysis\n" +
                "\n" +
                "doris_default_cluster_grocery_fin_cm_doris\n" +
                "\n" +
                "doris_default_cluster_grocery_predict_new\n" +
                "\n" +
                "doris_default_cluster_hwb_test_stage_stage_offline\n" +
                "\n" +
                "doris_default_cluster_jrc_mart_doris_online\n" +
                "\n" +
                "doris_default_cluster_powerbank_doris_prd\n" +
                "\n" +
                "doris_default_cluster_powerbank_iot_doris_offline\n" +
                "\n" +
                "doris_default_cluster_shangou_data_doris_app\n" +
                "\n" +
                "doris_default_cluster_test_db\n" +
                "\n" +
                "doris_default_cluster_wmrisk_data\n" +
                "\n" +
                "doris_fsp_doris_pay_fe\n" +
                "\n" +
                "doris_fsp_fspinno\n" +
                "\n" +
                "doris_fsp_fsppay\n" +
                "\n" +
                "doris_fsp_maiton\n" +
                "\n" +
                "doris_fsp_ml_rtdw\n" +
                "\n" +
                "doris_hotel_deep_hotel_report_deep_core_info_sd\n" +
                "\n" +
                "doris_hotelofflinedata_hotelofflinedata";
        String nn = sss.replace("\n\n","\',\'");

        System.out.println(nn);

        String dd = "doris_peisongbi_rt_peisong_rt\n" +
                "doris_default_cluster_wm_ba_stage_offline\n" +
                "doris_default_cluster_wm_ba\n" +
                "doris_default_cluster_testgrocery_doris_sc\n" +
                "doris_default_cluster_test_grocery_sc_meta\n" +
                "doris_default_cluster_test_grocery_doris_sup\n" +
                "doris_default_cluster_test_grocery_doris_sc2_analysis\n" +
                "doris_default_cluster_test_grocery_doris_sc2\n" +
                "doris_default_cluster_test_grocery_doris_sc_analysis\n" +
                "doris_default_cluster_test_grocery_doris_pdt_analysis\n" +
                "doris_default_cluster_test_grocery_doris_op\n" +
                "doris_default_cluster_shangou_rt_doris_stage_offline\n" +
                "doris_default_cluster_shangou_rt_doris\n" +
                "doris_default_cluster_sgbranddata_test_stage\n" +
                "doris_default_cluster_sgbranddata\n" +
                "doris_default_cluster_peisong_rt_dw\n" +
                "doris_default_cluster_peisong_bi\n" +
                "doris_default_cluster_health_data_doris_pharm_service_stage_offline\n" +
                "doris_default_cluster_health_data_doris_pharm_service\n" +
                "doris_default_cluster_health_data_doris_edi_app_stage_offline\n" +
                "doris_default_cluster_health_data_doris_edi_app\n" +
                "doris_default_cluster_health_data_doris_business_service_stage_offline\n" +
                "doris_default_cluster_health_data_doris_business_service\n" +
                "doris_default_cluster_health_data_doris_bianque_price_stage_offline\n" +
                "doris_default_cluster_health_data_doris_bianque_price\n" +
                "doris_default_cluster_grocery_sc_meta\n" +
                "doris_default_cluster_grocery_fin_sc_doris_stage_offline\n" +
                "doris_default_cluster_grocery_fin_sc_doris\n" +
                "doris_default_cluster_grocery_doris_tx\n" +
                "doris_default_cluster_grocery_doris_sup\n" +
                "doris_default_cluster_grocery_doris_sc2_analysis\n" +
                "doris_default_cluster_grocery_doris_sc2\n" +
                "doris_default_cluster_grocery_doris_sc_nrt\n" +
                "doris_default_cluster_grocery_doris_sc_analysis\n" +
                "doris_default_cluster_grocery_doris_sc\n" +
                "doris_default_cluster_grocery_doris_pdt_stage_offline\n" +
                "doris_default_cluster_grocery_doris_pdt_analysis\n" +
                "doris_default_cluster_grocery_doris_pdt\n" +
                "doris_default_cluster_grocery_doris_op\n" +
                "doris_default_cluster_app_peisong_core\n";
        String cdc = dd.replace("\n","','");
        System.out.println(cdc);
    }
}
