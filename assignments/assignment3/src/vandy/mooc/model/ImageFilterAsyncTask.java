package vandy.mooc.model;

import java.io.File;

import vandy.mooc.common.BitmapUtils;
import vandy.mooc.presenter.ImagePresenter;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

public class ImageFilterAsyncTask extends AsyncTask<Uri, Void, Uri> {

	private Context mContext;
	private Uri mImageUri;
	private Uri mDirectoryPath;
	private ImagePresenter mPresenter;

	public ImageFilterAsyncTask(Context context, Uri directoryPath,
			ImagePresenter presenter) {
		mContext = context;
		mDirectoryPath = directoryPath;
		mPresenter = presenter;
	}

	@Override
	protected Uri doInBackground(Uri... params) {
		try {
			Uri filteredImageUri = BitmapUtils.grayScaleFilter(mContext,
					params[0], mDirectoryPath);
			
			File file = new File(params[0].getPath());
			boolean deleted = file.delete();

			return filteredImageUri;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Uri result) {
		mPresenter.onProcessingComplete(result, result);
	}

}
