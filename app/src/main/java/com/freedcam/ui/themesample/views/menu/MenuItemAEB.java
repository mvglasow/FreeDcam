/*
 *
 *     Copyright (C) 2015 Ingo Fuchs
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * /
 */

package com.freedcam.ui.themesample.views.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.freedcam.apis.basecamera.camera.AbstractCameraUiWrapper;
import com.freedcam.utils.AppSettingsManager;
import com.troop.freedcam.R;

/**
 * Created by Ar4eR on 05.02.16.
 */
public class MenuItemAEB extends LinearLayout {
    private Button plus;
    private Button minus;
    private EditText editText;
    private Context context;

    private int min = -10;
    private int max = 10;
    private final int step = 1;
    private int current;
    private AbstractCameraUiWrapper cameraUiWrapper;
    private String settingsname;
    private AppSettingsManager appSettingsManager;


    public MenuItemAEB(Context context) {
        super(context);
        init(context);
    }

    public MenuItemAEB(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }
    private void init(Context context)
    {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_childs_number, this);
        this.plus = (Button)findViewById(R.id.button_plus);
        this.minus = (Button)findViewById(R.id.button_minus);
        this.editText = (EditText)findViewById(R.id.editText_number);
        /*this.plus.setClickable(true);
        this.minus.setClickable(true);
        this.plus.setEnabled(true);
        this.minus.setEnabled(true);*/

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((current - step) >= min)
                    current -= step;
                setCurrent(current);
            }
        });
        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current + step <= max)
                    current += step;
                setCurrent(current);

            }
        });

    }

    public void SetCameraUIWrapper(AbstractCameraUiWrapper cameraUiWrapper)
    {
        if (cameraUiWrapper == this.cameraUiWrapper)
            return;
        this.cameraUiWrapper = cameraUiWrapper;
        if (cameraUiWrapper !=  null && cameraUiWrapper.parametersHandler != null && cameraUiWrapper.parametersHandler.ManualExposure != null)
        {
            String[] v = cameraUiWrapper.parametersHandler.ManualExposure.getStringValues();
            int le = v.length;
            min = -(le/2);
            max = le/2;
            setCurrent(current);
        }
        //else
            //this.setVisibility(GONE);

    }

    private void setCurrent(int current) {
        String tempcurrent = String.valueOf(current);
        appSettingsManager.setString(settingsname, tempcurrent);
        if (cameraUiWrapper != null && cameraUiWrapper.parametersHandler != null && cameraUiWrapper.parametersHandler.captureBurstExposures != null) {
            if ((cameraUiWrapper.parametersHandler.captureBurstExposures.IsSupported())) {
                cameraUiWrapper.parametersHandler.captureBurstExposures.SetValue("on", true);
            }
            editText.setText(current +"");
        }
    }

    public void SetStuff(AppSettingsManager appSettingsManager, String settingvalue) {

        this.settingsname = settingvalue;
        this.appSettingsManager = appSettingsManager;
        String exp="";
        if (appSettingsManager != null)
        exp = appSettingsManager.getString(settingsname);
        if (exp == null || exp.equals("")) {
            exp = "0";
            current = Integer.parseInt(exp);
            setCurrent(current);
        }
        editText.setText(exp);
        current = Integer.parseInt(exp);
    }
}
