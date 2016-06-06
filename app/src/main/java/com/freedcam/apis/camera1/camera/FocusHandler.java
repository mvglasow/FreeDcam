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

package com.freedcam.apis.camera1.camera;

import android.hardware.Camera;
import android.view.MotionEvent;

import com.freedcam.apis.basecamera.camera.AbstractFocusHandler;
import com.freedcam.apis.basecamera.camera.FocusRect;
import com.freedcam.apis.basecamera.camera.modules.CameraFocusEvent;
import com.freedcam.apis.basecamera.camera.modules.I_Callbacks;
import com.freedcam.apis.basecamera.camera.parameters.AbstractParameterHandler;
import com.freedcam.apis.basecamera.camera.parameters.modes.AbstractModeParameter;
import com.freedcam.utils.DeviceUtils;

import java.util.List;

/**
 * Created by troop on 02.09.2014.
 */
public class FocusHandler extends AbstractFocusHandler implements I_Callbacks.AutoFocusCallback
{
    final String TAG = FocusHandler.class.getSimpleName();
    private final CameraHolder cameraHolder;
    private final CameraUiWrapper cameraUiWrapper;
    private final AbstractParameterHandler parametersHandler;

    int count;
    List<Camera.Area> areas;
    private boolean isFocusing = false;

    private boolean aeMeteringSupported =false;

    public AbstractModeParameter.I_ModeParameterEvent focusModeListner = new AbstractModeParameter.I_ModeParameterEvent() {
        @Override
        public void onValueChanged(String val)
        {
            if (cameraHolder.DeviceFrameWork != CameraHolder.Frameworks.MTK) {
                if (val.equals("auto") || val.equals("macro") || val.equals("touch")) {
                    if (focusEvent != null)
                        focusEvent.TouchToFocusSupported(true);
                } else {
                    if (focusEvent != null)
                        focusEvent.TouchToFocusSupported(false);
                }
            }
            else {
                if (focusEvent != null) {
                    aeMeteringSupported = true;
                    focusEvent.AEMeteringSupported(true);
                }
            }

        }

        @Override
        public void onIsSupportedChanged(boolean isSupported) {

        }

        @Override
        public void onIsSetSupportedChanged(boolean isSupported) {

        }

        @Override
        public void onValuesChanged(String[] values) {

        }

        @Override
        public void onVisibilityChanged(boolean visible) {

        }
    };
    public AbstractModeParameter.I_ModeParameterEvent aeModeListner = new AbstractModeParameter.I_ModeParameterEvent() {
        @Override
        public void onValueChanged(String val)
        {
            if(cameraHolder.DeviceFrameWork != CameraHolder.Frameworks.MTK)
            {
                if (val.contains("spot")) {
                    if (focusEvent != null) {
                        aeMeteringSupported = true;
                        focusEvent.AEMeteringSupported(true);
                    }
                } else {
                    if (focusEvent != null) {
                        aeMeteringSupported = false;
                        focusEvent.AEMeteringSupported(false);
                    }
                }
            }
            else
            {
                if (focusEvent != null) {
                    aeMeteringSupported = true;
                    focusEvent.AEMeteringSupported(true);
                }
            }

        }

        @Override
        public void onIsSupportedChanged(boolean isSupported) {

        }

        @Override
        public void onIsSetSupportedChanged(boolean isSupported) {

        }

        @Override
        public void onValuesChanged(String[] values) {

        }

        @Override
        public void onVisibilityChanged(boolean visible) {

        }
    };

    public FocusHandler(CameraUiWrapper cameraUiWrapper)
    {
        this.cameraUiWrapper = cameraUiWrapper;
        this.cameraHolder = cameraUiWrapper.cameraHolder;
        this.parametersHandler = cameraUiWrapper.parametersHandler;
    }

    @Override
    public boolean isAeMeteringSupported()
    {
        return aeMeteringSupported;
    }

    @Override
    public boolean isWbMeteringSupported() {
        return false;
    }

    @Override
    public void SetMotionEvent(MotionEvent event) {

    }

    @Override
    public void onAutoFocus(CameraFocusEvent event)
    {
        //camera.cancelAutoFocus();
        isFocusing = false;
        if (focusEvent != null)
            focusEvent.FocusFinished(event.success);
    }

    @Override
    public void onFocusLock(boolean locked) {

    }

    public void StartFocus()
    {
        if (focusEvent != null)
        {
            focusEvent.FocusStarted(null);
        }
        cameraHolder.StartFocus(this);
        isFocusing = true;
    }


    public void StartTouchToFocus(FocusRect rect, FocusRect meteringarea, int width, int height)
    {
        if (parametersHandler == null || cameraUiWrapper == null || cameraHolder == null || parametersHandler.FocusMode == null)
            return;

        String focusmode = parametersHandler.FocusMode.GetValue();
        if (focusmode.equals("auto") || focusmode.equals("macro"))
        {
           /* try {
                if (parametersHandler.ExposureLock != null && parametersHandler.ExposureLock.IsSupported()) {
                    if (parametersHandler.ExposureLock.GetValue().equals("true")) {
                        parametersHandler.ExposureLock.SetValue("false", true);
                        parametersHandler.ExposureLock.BackgroundValueHasChanged("false");
                    }
                }
            }
            catch (Exception ex)
            {
                
            }*/
            final FocusRect targetFocusRect = getFocusRect(rect, width, height);


            if (targetFocusRect.left >= -1000
                    && targetFocusRect.top >= -1000
                    && targetFocusRect.bottom <= 1000
                    && targetFocusRect.right <= 1000)
            {

                parametersHandler.SetFocusAREA(targetFocusRect, meteringarea);
                if (cameraHolder != null)
                    cameraHolder.StartFocus(this);
                isFocusing = true;
                if (focusEvent != null)
                    focusEvent.FocusStarted(rect);
            }
        }

    }

    @Override
    public void SetMeteringAreas(FocusRect meteringRect, int width, int height)
    {
        if (DeviceUtils.IS(DeviceUtils.Devices.ZTE_ADV))
        {
            final FocusRect targetFocusRect = getFocusRect(meteringRect, width, height);
            parametersHandler.SetMeterAREA(targetFocusRect);
        }
        else {
            final FocusRect targetFocusRect = getFocusRect(meteringRect, width, height);
            cameraHolder.SetMeteringAreas(targetFocusRect);
        }

    }

    @Override
    public void SetAwbAreas(FocusRect awbRect, int width, int height) {

    }

    private FocusRect getFocusRect(FocusRect rect, int width, int height)
    {
        logFocusRect(rect);
        if (width == 0 || height == 0)
            return null;
        final FocusRect targetFocusRect = new FocusRect(
                rect.left * 2000 / width - 1000,
                rect.right * 2000 / width - 1000,
                rect.top * 2000 / height - 1000,
                rect.bottom * 2000 / height - 1000,
                rect.x,rect.y);
        logFocusRect(targetFocusRect);
        //check if stuff is to big or to small and set it to min max key_value
        if (targetFocusRect.left < -1000)
        {
            int dif = targetFocusRect.left + 1000;
            targetFocusRect.left = -1000;
            targetFocusRect.right += dif;
        }
        if (targetFocusRect.right > 1000)
        {
            int dif = targetFocusRect.right - 1000;
            targetFocusRect.right = 1000;
            targetFocusRect.left -= dif;
        }
        if (targetFocusRect.top < -1000)
        {
            int dif = targetFocusRect.top + 1000;
            targetFocusRect.top = -1000;
            targetFocusRect.bottom += dif;
        }
        if (targetFocusRect.bottom > 1000)
        {
            int dif = targetFocusRect.bottom -1000;
            targetFocusRect.bottom = 1000;
            targetFocusRect.top -=dif;
        }
        return targetFocusRect;
    }
}
