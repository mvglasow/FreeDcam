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

package freed.cam.apis.camera1.parameters.device.qualcomm.LG;

import android.hardware.Camera.Parameters;

import freed.cam.apis.basecamera.CameraWrapperInterface;
import freed.cam.apis.basecamera.parameters.manual.AbstractManualParameter;
import freed.cam.apis.basecamera.parameters.manual.ManualParameterInterface;
import freed.cam.apis.camera1.parameters.device.BaseQcomNew;
import freed.cam.apis.camera1.parameters.manual.lg.AE_Handler_LGG4;
import freed.cam.apis.camera1.parameters.manual.lg.CCTManualG4;

/**
 * Created by troop on 01.06.2016.
 */
public class LG_G4 extends BaseQcomNew
{
    private final AE_Handler_LGG4 ae_handler_lgg4;
    public LG_G4(Parameters parameters, CameraWrapperInterface cameraUiWrapper) {
        super(parameters,cameraUiWrapper);
        ae_handler_lgg4 = new AE_Handler_LGG4(parameters, cameraUiWrapper);
        parameters.set("lge-camera","1");
    }


    @Override
    public ManualParameterInterface getIsoParameter() {
        return ae_handler_lgg4.getManualIso();
    }


    @Override
    public AbstractManualParameter getCCTParameter() {
        return new CCTManualG4(parameters, cameraUiWrapper);
    }

}
