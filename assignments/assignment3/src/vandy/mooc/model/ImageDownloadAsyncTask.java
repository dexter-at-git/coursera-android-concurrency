package vandy.mooc.model;

import vandy.mooc.presenter.ImagePresenter;
import vandy.mooc.utils.loader.ImageLoaderThreadPool;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

public class ImageDownloadAsyncTask extends AsyncTask<Uri, Void, Uri> {

	private Context mContext;
	private Uri mImageUri;
	private Uri mDirectoryPath;
	private ImagePresenter mPresenter;

	public ImageDownloadAsyncTask(Context context, Uri directoryPath,
			ImagePresenter presenter) {
		mContext = context;
		mDirectoryPath = directoryPath;
		mPresenter = presenter;
	}

	@Override
	protected Uri doInBackground(Uri... params) {
		try {
			ImageDownloadsModel model = new ImageDownloadsModel();
			Uri downloadedImageUri = model.downloadImage(mContext, params[0],
					mDirectoryPath);

			return downloadedImageUri;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Uri result) {
		// mPresenter.onProcessingComplete(result, result);
		ImageFilterAsyncTask filterTask = new ImageFilterAsyncTask(mContext,
				mDirectoryPath, mPresenter);
		filterTask.executeOnExecutor(
				ImageLoaderThreadPool.MY_THREAD_POOL_EXECUTOR, result);
	}

}
