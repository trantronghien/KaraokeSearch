package com.example.admin.karaokesearch.manager;

import android.util.Log;

import com.example.admin.karaokesearch.util.ErrorObject;
import com.example.admin.karaokesearch.util.UtilHelper;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by admin on 21/10/2017.
 */

public abstract class BaseHelperManager<T extends AbstractDao>  implements IHelperManager {

    public abstract T getDaoSession();
    protected AbstractDao daoSession;
    public static boolean isDebugDataBase = false;
    private ErrorObject errorObject;
    protected int limit = 30;

    protected BaseHelperManager(){
        errorObject = new ErrorObject();
        daoSession = getDaoSession();
        daoSession.queryBuilder().LOG_SQL = isDebugDataBase;
        daoSession.queryBuilder().LOG_VALUES = isDebugDataBase;
    }

    public void setDebugDataBase(boolean isDebugDataBase){
        this.isDebugDataBase =  isDebugDataBase;
    }

    public ErrorObject getErrorManager(){
        errorObject.errorCode = UtilHelper.ERROR_CODE_MANAGER;
        errorObject.errorMess = this.getClass().getCanonicalName();
        return errorObject;
    }
    public void showErrorLog(){
        Log.e("error", errorObject.errorMess);
    }

    /**
     *  set limit object when request default limit 30
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
