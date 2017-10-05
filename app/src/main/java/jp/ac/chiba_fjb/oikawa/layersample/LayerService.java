package jp.ac.chiba_fjb.oikawa.layersample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class LayerService extends Service {
    private View mView;
    public LayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    void removeLayer(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(mView);
    }
    void showLayer(){
        // Viewからインフレータを作成する
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        // 重ね合わせするViewの設定を行う
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
              WindowManager.LayoutParams.WRAP_CONTENT,
              WindowManager.LayoutParams.WRAP_CONTENT,
              WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                  WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED|
                  WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
                  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                      | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
              PixelFormat.TRANSPARENT);


        // WindowManagerを取得する
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // レイアウトファイルから重ね合わせするViewを作成する
        mView = layoutInflater.inflate(R.layout.layer, null);
        //イベントサンプル
        mView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = mView.findViewById(R.id.textView);
                textView.setText("ボタンが押されました");
            }
        });

        //レイアウトのサイズを確定させる
        mView.measure(-1,-1);
        // Viewを画面上に重ね合わせする
        wm.addView(mView, params);

        //レイアウトサイズの取得
        int width = mView.getMeasuredWidth();
        int height = mView.getMeasuredHeight();

        //スクリーンサイズの取得
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);

        //0,0が中心位置の設定なので、左上を0,0に補正する為の値
        int baseX = -(screenSize.x-width)/2;
        int baseY = -(screenSize.y-height)/2;

        params.x = baseX + screenSize.x - width;
        params.y = baseY + screenSize.y - height*2;
        wm.updateViewLayout(mView, params);



    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("サービス開始");
        showLayer();


    }

    @Override
    public void onDestroy() {
        System.out.println("サービス停止");
        removeLayer();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction() == null)
            return super.onStartCommand(intent, flags, startId);;
        switch(intent.getAction()){
            case "START":
                break;
            case "STOP":
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
