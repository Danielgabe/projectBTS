 
package com.nichtemna.takeandsavephoto;

import java.io.File;
import java.util.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nichtemna.takeandsavephoto.PhotoActivity.TransactionType;
import com.nichtemna.takeandsavephoto.fragment.CommonUtil;
import com.nichtemna.takeandsavephoto.fragment.DataPhoto;
import com.nichtemna.takeandsavephoto.fragment.StatusAmbilSurvey;
import com.nichtemna.takeandsavephoto.fungsi.DatabaseManager;

public class PhotoFragment extends Fragment implements OnClickListener {
	private DatabaseManager dm;
    public enum ImageSetMode {
        LAST, LEFT, RIGHT, POSITION
    }

    private final static int PERIOD = 500;
    private final static int TICK = 250;

    private ImageButton imb_remove, imb_camera;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private ArrayList<File> files = new ArrayList<File>();
    private ArrayList<File> filterfiles = new ArrayList<File>();

    public static PhotoFragment newInstance(TransactionType type) {
        PhotoFragment frag = new PhotoFragment();
        final Bundle args = new Bundle();
        args.putSerializable(Extras.TRANSACTION_TYPE, type);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        initViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (getTransactionType().equals(TransactionType.TEMPRORAILY)) {
            cameraCountDownTimer.start();
        }
        
        dm = new DatabaseManager(getActivity());

        getFileList();


        super.onActivityCreated(savedInstanceState);
    }

    public class CustomComparator implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            return (int) (file2.lastModified() - file1.lastModified());
        }
    }

    private void initViews(View view) {
        imb_remove = (ImageButton) view.findViewById(R.id.frag_photo_imb_remove_photo);
        imb_camera = (ImageButton) view.findViewById(R.id.frag_photo_imb_camera);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void setListeners() {
        imb_remove.setOnClickListener(this);
        imb_camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_photo_imb_remove_photo:
                removeCurrentImage();
                break;

            case R.id.frag_photo_imb_camera:
                goToCameraFragment();
                break;
        }
        cameraCountDownTimer.cancel();
    }

    /**
     * Switch fragment to photo preview
     */
    private void goToCameraFragment() {
        if (getActivity() instanceof FragmentToggler) {
            ((FragmentToggler) getActivity()).toggleFragments(TransactionType.PERMANENT);
        }
    }

    /**
     * Deletes current image form storage
     */
    private void removeCurrentImage() {
    	int currentFilePosition = viewPager.getCurrentItem();
    	filterfiles.get(currentFilePosition).delete();
        Toast.makeText(getActivity(), "Deleted " + filterfiles.get(currentFilePosition).getName(), Toast.LENGTH_SHORT).show();
        
        if(StatusAmbilSurvey.getActiveCamera().equals("AKSESPLN")){
        	dm.updateDataPhotoByButton("param_2", StatusAmbilSurvey.getButtonnya(), "", StatusAmbilSurvey.getNomid());
        }else if(StatusAmbilSurvey.getActiveCamera().equals("POLEPLN")){
        	dm.updateDataPhotoByButton("param_3", StatusAmbilSurvey.getButtonnya(), "", StatusAmbilSurvey.getNomid());
        }else if(StatusAmbilSurvey.getActiveCamera().equals("KABELPLN")){
        	dm.updateDataPhotoByButton("param_4", StatusAmbilSurvey.getButtonnya(), "", StatusAmbilSurvey.getNomid());
        }else if(StatusAmbilSurvey.getActiveCamera().equals("PANORAMIC")){
        	String files = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya()).getPanoramic();
        	if(CommonUtil.isNotNullOrEmpty(files)){
        		String lastFiles = files.replaceAll(filterfiles.get(currentFilePosition).getName()+";", "");
        		dm.updateDataPhotoByButton("panoramic", StatusAmbilSurvey.getButtonnya(), lastFiles, StatusAmbilSurvey.getNomid());
        	}
        }
        getFileList();
        //  adapter.notifyDataSetChanged();
    }

    private TransactionType getTransactionType() {
        return (TransactionType) getArguments().getSerializable(Extras.TRANSACTION_TYPE);
    }

    /**
     * If TransactionType is TEMPRORAILY, after timer finished we go back to {@link CameraFragment}
     */
    private CountDownTimer cameraCountDownTimer = new CountDownTimer(PERIOD, TICK) {

        public void onTick(long millisUntilFinished) {
        }

        public void onFinish() {
            goToCameraFragment();
        }
    };

    /**
     * Gets list of files in this folder
     */
    private void getFileList() {
        files.clear();
        filterfiles.clear();
        //File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO);
        //Collections.addAll(files, root.listFiles());
        DataPhoto dp = dm.getDataPhotoByNomButton(StatusAmbilSurvey.getNomid(), StatusAmbilSurvey.getButtonnya());
        String listPanoramicFiles = "";
        
        /*for (File file : files) {
			Log.d("Get Image File", files.toString());
			String[] parts = file.getName().split("_");
			if(
					parts[0].equals(StatusAmbilSurvey.getUserid()) && parts[1].equals(StatusAmbilSurvey.getActiveCamera()) &&
							parts[2].equals(String.valueOf(StatusAmbilSurvey.getNomid())) && parts[3].equals(StatusAmbilSurvey.getButtonnya())
					){
				filterfiles.add(file);
				listPanoramicFiles += file.getName()+";";
			}
        }*/
        
        if(StatusAmbilSurvey.getActiveCamera().equals("PANORAMIC"))
        {
        	String filenames = dp.getPanoramic();
        	if(CommonUtil.isNotNullOrEmpty(filenames)){
        		String split[] = filenames.split(";");
        		int countSplit = split.length;
        		for(int a=0; a<countSplit; a++){
        			File toget = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
        					split[a]);
        			filterfiles.add(toget);
        		}
        	}
        	
        }else if(StatusAmbilSurvey.getActiveCamera().equals("AKSESPLN"))
        {
        	String filename = dp.getParam_2();
        	if(CommonUtil.isNotNullOrEmpty(filename)){
        		File toget = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
        				filename);
        		filterfiles.add(toget);
        	}
        }else if(StatusAmbilSurvey.getActiveCamera().equals("POLEPLN"))
        {
        	String filename = dp.getParam_3();
        	if(CommonUtil.isNotNullOrEmpty(filename)){
        		File toget = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
        				filename);
        		filterfiles.add(toget);
        	}
        }else if(StatusAmbilSurvey.getActiveCamera().equals("KABELPLN"))
        {
        	String filename = dp.getParam_4();
        	if(CommonUtil.isNotNullOrEmpty(filename)){
        		File toget = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + ImageUtils.TAKE_AND_SAVE_PHOTO).toString(),
        				filename);
        		filterfiles.add(toget);
        	}
        }
        
        Collections.sort(filterfiles, new CustomComparator());
        
        adapter = new ViewPagerAdapter(getActivity(), filterfiles);
        viewPager.setAdapter(adapter);
        
        imb_remove.setVisibility(filterfiles.size() > 0 ? View.VISIBLE : View.INVISIBLE);
    }
}
