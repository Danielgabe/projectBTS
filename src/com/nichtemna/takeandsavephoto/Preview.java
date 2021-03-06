package com.nichtemna.takeandsavephoto;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class Preview extends ViewGroup implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private Camera.Size mPreviewSize;
    private List<Camera.Size> mSupportedPreviewSizes;
    private Camera mCamera;
    private int mCameraID = 0;
    private Context context;
    private String logMessage;

    public Preview(Context context, SurfaceView surfaceView) {
        super(context);
        this.context = context;
        mSurfaceView = surfaceView;
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setmCameraID(int mCameraID) {
        this.mCameraID = mCameraID;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        if (mCamera != null) {
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
            requestLayout();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed && getChildCount() > 0) {
            final View child = getChildAt(0);

            final int width = r - l;
            final int height = b - t;

            int previewWidth = width;
            int previewHeight = height;
            if (mPreviewSize != null) {
                previewWidth = mPreviewSize.width;
                previewHeight = mPreviewSize.height;
                logMessage += "hasil test-camera mPreviewSize : "+previewWidth+" "+previewHeight+"\n";
            }

            
            if (width * previewHeight > height * previewWidth) {
                final int scaledChildWidth = previewWidth * height / previewHeight;
                child.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height);
                logMessage += "hasil test-camera if : "+width+" "+scaledChildWidth+"\n";
            } else {
                final int scaledChildHeight = previewHeight * width / previewWidth;
                child.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2);
                logMessage += "hasil test-camera else : "+width+" "+scaledChildHeight+"\n";
            }
            //createLog(logMessage);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(mCameraID);
            }
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            Log.e("TAG", "IOException caused by setPreviewDisplay()", exception);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        logMessage += "hasil test-camera-preview-size : "+optimalSize.height+" "+optimalSize.width;
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(mCameraID);
            }
            Camera.Parameters parameters = mCamera.getParameters();
            mCamera.setDisplayOrientation(90);
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            mCamera.setParameters(parameters);
            requestLayout();
            previewCamera();
            
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void previewCamera() {
        try {
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
            
        }
    }
    
    private void createLog(String msg){
    	FileHandler fh=null;
        String name;
        if ( 0 == Environment.getExternalStorageState().compareTo(Environment.MEDIA_MOUNTED))
            name = Environment.getExternalStorageDirectory().getAbsolutePath();
        else
            name = Environment.getDataDirectory().getAbsolutePath();

        name += "/Preview.log";

        try {
            fh = new FileHandler(name, 256*1024, 1, true);
            fh.setFormatter(new SimpleFormatter());
            fh.publish(new LogRecord(Level.ALL, " : "+msg));
        } catch (Exception e) 
        {
            Log.e("Preview", "FileHandler exception", e);
            return;
        }finally{
            if (fh != null)
                fh.close();
        }
    }
    
}