package troop.com.themesample.subfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.troop.freedcam.i_camera.parameters.ApiParameter;
import com.troop.freedcam.i_camera.parameters.HistogramParameter;
import com.troop.freedcam.i_camera.parameters.ParameterExternalShutter;
import com.troop.freedcam.ui.AbstractFragment;
import com.troop.freedcam.ui.AppSettingsManager;

import troop.com.themesample.R;
import troop.com.themesample.views.menu.MenuItemBayerFormat;
import troop.com.themesample.views.menu.MenuItemGPS;
import troop.com.themesample.views.menu.MenuItemOrientationHack;
import troop.com.themesample.views.menu.MenuItemSDSave;
import troop.com.themesample.views.menu.MenuItemTimeLapseFrames;
import troop.com.themesample.views.menu.MenuItemVideoProfile;
import troop.com.themesample.views.uichilds.UiSettingsChild;
import troop.com.themesample.views.menu.MenuItemTheme;

/**
 * Created by troop on 15.06.2015.
 */
public class LeftMenuFragment extends AbstractFragment  implements Interfaces.I_MenuItemClick
{
    MenuItemTheme themeItem;
    MenuItemBayerFormat bayerFormatItem;
    troop.com.themesample.views.menu.MenuItem pictureSize;
    MenuItemSDSave sdSave;
    MenuItemGPS menuItemGPS;
    troop.com.themesample.views.menu.MenuItem guide;
    troop.com.themesample.views.menu.MenuItem api;
    troop.com.themesample.views.menu.MenuItem externalShutter;
    MenuItemOrientationHack orientationHack;

    troop.com.themesample.views.menu.MenuItem jpegQuality;
    troop.com.themesample.views.menu.MenuItem histogram;
    //troop.com.themesample.views.menu.MenuItem focuspeak;

    troop.com.themesample.views.menu.MenuItem aeBracket;

    MenuItemVideoProfile videoProfile;
    troop.com.themesample.views.menu.MenuItemVideoHDR videoHDR;
    MenuItemTimeLapseFrames timeLapseFrames;

    troop.com.themesample.views.menu.MenuItem OverrideVideoProfile;
    troop.com.themesample.views.menu.MenuItem VideoWidth;
    troop.com.themesample.views.menu.MenuItem VideoHeight;
    troop.com.themesample.views.menu.MenuItem VideoFps;
    troop.com.themesample.views.menu.MenuItem VideoBitrate;
    troop.com.themesample.views.menu.MenuItem VideoHFR;
    troop.com.themesample.views.menu.MenuItem VideoHSR;
    troop.com.themesample.views.menu.MenuItem VideoCodec;
    troop.com.themesample.views.menu.MenuItem AudioBitrate;
    troop.com.themesample.views.menu.MenuItem AudioSampleRate;
    troop.com.themesample.views.menu.MenuItem AudioCodec;


    Interfaces.I_MenuItemClick onMenuItemClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.view = inflater.inflate(R.layout.leftmenufragment, container, false);
        setWrapper();
        return view;
    }

    private void setWrapper()
    {
        themeItem = (MenuItemTheme)view.findViewById(R.id.MenuItemTheme);
        themeItem.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_Theme);
        themeItem.SetParameter(wrapper.camParametersHandler.ThemeList);
        themeItem.SetMenuItemListner(this);

        bayerFormatItem = (MenuItemBayerFormat)view.findViewById(R.id.MenuItemBayerFormat);
        bayerFormatItem.SetStuff(i_activity, appSettingsManager, "");
        bayerFormatItem.SetParameter(wrapper.camParametersHandler.PictureFormat);
        bayerFormatItem.SetMenuItemListner(this);

        pictureSize = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemPicSize);
        pictureSize.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_PICTURESIZE);
        pictureSize.SetParameter(wrapper.camParametersHandler.PictureSize);
        pictureSize.SetMenuItemListner(this);

        sdSave = (MenuItemSDSave)view.findViewById(R.id.MenuItemSDSave);
        sdSave.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_EXTERNALSD);
        sdSave.SetCameraUiWrapper(wrapper);
        sdSave.SetMenuItemListner(this);

        menuItemGPS = (MenuItemGPS)view.findViewById(R.id.MenuItemGPS);
        menuItemGPS.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_LOCATION);
        menuItemGPS.SetCameraUIWrapper(wrapper);
        menuItemGPS.SetMenuItemListner(this);

        guide = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemGuide);
        guide.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_GUIDE);
        guide.SetParameter(wrapper.camParametersHandler.GuideList);
        guide.SetMenuItemListner(this);

        api = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemApi);
        api.SetStuff(i_activity, appSettingsManager, null);
        api.SetParameter(new ApiParameter(null, i_activity, appSettingsManager));
        api.SetMenuItemListner(this);

        externalShutter = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemExternalShutter);
        externalShutter.SetStuff(i_activity, appSettingsManager, null);
        externalShutter.SetParameter(new ParameterExternalShutter(appSettingsManager));
        externalShutter.SetMenuItemListner(this);

        orientationHack = (MenuItemOrientationHack)view.findViewById(R.id.MenuItemOrientationHack);
        orientationHack.SetStuff(i_activity, appSettingsManager, null);
        orientationHack.SetCameraUIWrapper(wrapper);
        orientationHack.SetMenuItemListner(this);

        jpegQuality = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemJpegQuality);
        jpegQuality.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_JPEGQUALITY);
        jpegQuality.SetParameter(wrapper.camParametersHandler.JpegQuality);
        jpegQuality.SetMenuItemListner(this);

        /*histogram = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemHistogram);
        histogram.SetStuff(i_activity,appSettingsManager, AppSettingsManager.SETTING_HISTOGRAM);
        histogram.SetParameter(new HistogramParameter(null, i_activity, appSettingsManager, wrapper));
        histogram.SetMenuItemListner(this);*/

        /*focuspeak = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemFocusPeak);
        focuspeak.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_FOCUSPEAK);
        focuspeak.SetParameter(wrapper.camParametersHandler.Focuspeak);
        focuspeak.SetMenuItemListner(this);*/



        aeBracket = (troop.com.themesample.views.menu.MenuItem)view.findViewById(R.id.MenuItemAeBracket);
        aeBracket.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_AEBRACKETACTIVE);
        aeBracket.SetParameter(wrapper.camParametersHandler.AE_Bracket);
        aeBracket.SetMenuItemListner(this);

        videoProfile = (MenuItemVideoProfile)view.findViewById(R.id.MenuItemVideoProfile);
        videoProfile.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_VIDEPROFILE);
        if (wrapper.camParametersHandler.VideoProfiles != null)
            videoProfile.SetParameter(wrapper.camParametersHandler.VideoProfiles);
        else if (wrapper.camParametersHandler.VideoProfilesG3 != null)
            videoProfile.SetParameter(wrapper.camParametersHandler.VideoProfilesG3);
        else
            videoProfile.SetParameter(null);
        videoProfile.SetMenuItemListner(this);

        videoHDR = (troop.com.themesample.views.menu.MenuItemVideoHDR)view.findViewById(R.id.MenuItemVideHDR);
        videoHDR.SetStuff(i_activity, appSettingsManager, AppSettingsManager.SETTING_VIDEOHDR);
        videoHDR.SetParameter(wrapper.camParametersHandler.VideoHDR);
        videoHDR.SetMenuItemListner(this);
        videoHDR.SetModulesToShow(wrapper.moduleHandler.VideoModules, wrapper.moduleHandler);

        timeLapseFrames = (MenuItemTimeLapseFrames)view.findViewById(troop.com.themesample.R.id.MenuItemTimeLapseFrame);
        timeLapseFrames.SetStuff(appSettingsManager, AppSettingsManager.SETTING_VIDEOTIMELAPSEFRAME);

    }

    public void SetMenuItemClickListner(Interfaces.I_MenuItemClick menuItemClick)
    {
        this.onMenuItemClick = menuItemClick;
    }



    @Override
    public void onMenuItemClick(UiSettingsChild item, boolean fromLeftFragment) {
        onMenuItemClick.onMenuItemClick(item, true);
    }
}
