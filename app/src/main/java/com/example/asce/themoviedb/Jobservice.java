package com.example.asce.themoviedb;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class Jobservice extends JobService {


    @Override
    public boolean onStartJob(JobParameters job) {
        return true;
    }
    @Override
    public boolean onStopJob(JobParameters job) {
        Log.e("sam" , "onstop called ");
        return false;
    }
}