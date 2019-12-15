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

package freed.cam.ui.ipaddresseditor;

import android.R.id;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.fragment.app.FragmentTransaction;

import freed.ActivityAbstract;
import freed.cam.ui.videoprofileeditor.VideoProfileEditorFragment;
import freed.utils.LocationManager;
import freed.viewer.holder.FileHolder;

/**
 * Created by troop on 15.02.2016.
 */
public class IPAddressEditorActivity extends ActivityAbstract
{
    private final String TAG = IPAddressEditorActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id.content, new IPAddressEditorFragment(), TAG);
            ft.commit();
        }
    }

    @Override
    protected void setContentToView() {

    }

    @Override
    public LocationManager getLocationManager() {
        return null;
    }

    @Override
    public void WorkHasFinished(FileHolder fileHolder) {

    }

    @Override
    public void WorkHasFinished(FileHolder[] fileHolder) {

    }
}
