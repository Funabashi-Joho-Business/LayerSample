package jp.ac.chiba_fjb.oikawa.layersample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	final static int OVERLAY_PERMISSION_REQ_CODE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					                          Uri.parse("package:" + getPackageName()));
				startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
		}
		else
			startService(new Intent(this, LayerService.class).setAction("START"));


	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123) {
			if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
				startService(new Intent(this, LayerService.class).setAction("START"));

			}
		}
	}
}
