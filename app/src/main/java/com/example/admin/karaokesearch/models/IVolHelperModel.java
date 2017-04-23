package com.example.admin.karaokesearch.models;

import com.example.admin.karaokesearch.models.Entities.SVol;

import java.util.ArrayList;

/**
 * Created by admin on 3/18/2017.
 */

public interface IVolHelperModel {
    ArrayList<SVol> getVolName(int idTable);
}
