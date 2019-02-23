package com.selva.lockscreen

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.selva.lockscreen.receiver.DeviceAdminReceiver


class LockActivity : AppCompatActivity() {

    private lateinit var mDevicePolicyManager: DevicePolicyManager
    private lateinit var mComponentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDevicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        mComponentName = ComponentName(this, DeviceAdminReceiver::class.java)

        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            mDevicePolicyManager.lockNow()
        } else {
            Toast.makeText(this, getString(R.string.activate_admin), Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.extra_explanation))
            startActivity(intent)
        }
        finish()
    }
}
