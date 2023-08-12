package com.thinking.my.string;

/**
 * @author liyong40
 * @Description
 * @date 2022/11/29
 */
public class Test {

    public static void main(String[] args) {
        String ss = "\t_mt_datetime \t_mt_servername \t_mt_appkey \t_mt_level \t_mt_thread \t_mt_action \t_mt_message \t_mt_key_base64 \t_mt_millisecond \tdomain \tdb_name \ttable_name \tquery_id \tinstance_id \tprofile_name \tid \ttotal_time \tlocal_time \tfragment_cpu_time \taverage_thread_tokens \trows_produced \tpeak_memory_usage \tpeak_reservation \tpeak_used_reservation \ttablet_count \trowset_num \tsegment_num \tnum_scanners \tstart_scan_time \twait_scan_time \tmax_wait_scan_time \trowset_reader_init_time \tget_next_time \tblock_lookup_cache_time \tblock_put_cache_time \tscanner_max_pending_timer \tscanner_block_put_timer \tbytes_read \trows_read \trows_returned \tscan_time \tscan_cpu_time \treader_init_time \tblock_convert_time \tblock_fetch_time \trows_del_filtered \trows_pushed_cond_filtered \tnum_segment_total \tnum_segment_filtered \ttotal_pages_num \tcached_pages_num \tblocks_load \tblock_seek_count \traw_rows_read \tcompressed_bytes_read \tuncompressed_bytes_read \trows_key_range_filtered \trows_conditions_filtered \trows_stats_filtered \trows_bloom_filter_filtered \trows_bitmap_index_filtered \trows_vector_pred_filtered \tiotimer \tdecompressor_timer \tblock_load_time \tblock_seek_time \tindex_load_time_v1 \tbitmap_index_filter_timer \tvector_pred_eval_time \tnum_disk_access \tbatch_queue_wait_time \tscanner_sched_count \tscanner_batch_wait_time \tscanner_worker_wait_time \tbytes_received \tdata_arrival_wait_time \tfirst_batch_arrival_wait_time \tsenders_blocked_total_timer \tdeserialize_row_batch_timer \tconvert_row_batch_time \tdecompress_bytes \tdecompress_time \tbytes_sent \tlocal_bytes_sent \tbrpc_send_time \tbrpc_send_time_wait \tcompress_time \tuncompressed_row_batch_size \tignore_rows \tserialize_batch_time \tdt \thour \tctime \n";


        ss =  ss.replace(" \t",",");
        System.out.println(ss);

    }
}
