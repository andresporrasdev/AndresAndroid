package algonquin.cst2335.porr0016.data;

import android.media.Image;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

//    public String editString;
    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>();
    public MutableLiveData<Image> image = new MutableLiveData<>();

    public MutableLiveData<Image> imageButton = new MutableLiveData<>();
}
