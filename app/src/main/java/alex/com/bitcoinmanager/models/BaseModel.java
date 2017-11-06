package alex.com.bitcoinmanager.models;

import alex.com.bitcoinmanager.interfaces.Listable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by Alex on 11/3/2017.
 */

public abstract class BaseModel implements Listable {
    public String id;
}
