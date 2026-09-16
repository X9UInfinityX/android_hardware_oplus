package android.os;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;

import java.util.Map;

public class OplusKeyEventManager {
    public static final String TAG = "OplusKeyEventManager";

    private static final String SHOULDER_PRESSURE_SERVICE =
            "OplusShoulderPressureManagerService";
    private static final String SHOULDER_PRESSURE_DESCRIPTOR =
            "com.oplus.shoulderpressure.IOplusShoulderPressureManager";
    private static final String KEY_EVENT_OBSERVER_DESCRIPTOR =
            "android.os.IOplusKeyEventObserver";
    private static final int TRANSACTION_REGISTER_KEY_EVENT_INTERCEPTOR = 1001;
    private static final int TRANSACTION_UNREGISTER_KEY_EVENT_INTERCEPTOR = 1002;
    private static final int TRANSACTION_REGISTER_KEY_EVENT_OBSERVER = 1003;
    private static final int TRANSACTION_UNREGISTER_KEY_EVENT_OBSERVER = 1004;

    public static final int INTERCEPT_ALWAYS = 0;
    public static final int INTERCEPT_ONCE = 1;

    public static final int LISTEN_ALL_KEY_EVENT = 0;
    public static final int LISTEN_POWER_KEY_EVENT = 1;
    public static final int LISTEN_VOLUME_UP_KEY_EVENT = 2;
    public static final int LISTEN_VOLUME_DOWN_KEY_EVENT = 4;
    public static final int LISTEN_MENU_KEY_EVENT = 8;
    public static final int LISTEN_HOME_KEY_EVENT = 16;
    public static final int LISTEN_BACK_KEY_EVENT = 32;
    public static final int LISTEN_F4_KEY_EVENT = 64;
    public static final int LISTEN_CAMERA_KEY_EVENT = 128;
    public static final int LISTEN_HEADSETHOOK_KEY_EVENT = 1024;
    public static final int LISTEN_VOLUME_MUTE_KEY_EVENT = 2048;
    public static final int LISTEN_APP_SWITCH_KEY_EVENT = 4096;
    public static final int LISTEN_WAKEUP_KEY_EVENT = 8192;
    public static final int LISTEN_BRIGHTNESS_UP_KEY_EVENT = 16384;
    public static final int LISTEN_BRIGHTNESS_DOWN_KEY_EVENT = 32768;
    public static final int LISTEN_ENDCALL_KEY_EVENT = 65536;
    public static final int LISTEN_SLEEP_KEY_EVENT = 131072;
    public static final int LISTEN_ACTION_BUTTON_SINGLE_TAP_KEY_EVENT = 262144;
    public static final int LISTEN_ACTION_BUTTON_LONG_PRESS_KEY_EVENT = 524288;
    public static final int LISTEN_LINGXI_GAME_KEY_EVENT = 16777216;
    public static final int LISTEN_SHOULDER_DOWN_KEY_EVENT = 33554432;
    public static final int LISTEN_SHOULDER_UP_KEY_EVENT = 67108864;
    public static final int LISTEN_LINGXI_NO_GAME_KEY_EVENT = 134217728;

    public int mVersion = 1;

    private static volatile OplusKeyEventManager sInstance;

    private final Map<String, KeyEventObserverDelegate> mKeyEventInterceptors =
            new ArrayMap<>();
    private final Map<OnKeyEventObserver, KeyEventObserverRegistration> mKeyEventObservers =
            new ArrayMap<>();

    private OplusKeyEventManager() {
    }

    public static OplusKeyEventManager getInstance() {
        if (sInstance == null) {
            synchronized (OplusKeyEventManager.class) {
                if (sInstance == null) {
                    sInstance = new OplusKeyEventManager();
                }
            }
        }
        return sInstance;
    }

    public boolean registerKeyEventObserver(Context context, OnKeyEventObserver observer, int listenFlag) {
        if (context == null || observer == null) {
            return false;
        }
        synchronized (mKeyEventObservers) {
            if (mKeyEventObservers.containsKey(observer)) {
                return false;
            }
            final IBinder service = ServiceManager.getService(SHOULDER_PRESSURE_SERVICE);
            if (service == null) {
                return false;
            }
            final String observerKey = observer.hashCode() + context.getPackageName()
                    + Process.myPid();
            final KeyEventObserverDelegate delegate = new KeyEventObserverDelegate(observer);
            final Parcel data = Parcel.obtain(service);
            final Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(SHOULDER_PRESSURE_DESCRIPTOR);
                data.writeString(observerKey);
                data.writeStrongBinder(delegate);
                data.writeInt(listenFlag);
                if (!service.transact(TRANSACTION_REGISTER_KEY_EVENT_OBSERVER,
                        data, reply, 0)) {
                    return false;
                }
                reply.readException();
                final boolean registered = reply.readBoolean();
                if (registered) {
                    mKeyEventObservers.put(observer,
                            new KeyEventObserverRegistration(observerKey, delegate));
                }
                return registered;
            } catch (RemoteException | RuntimeException e) {
                Log.e(TAG, "Unable to register key-event observer", e);
                return false;
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    public boolean unregisterKeyEventObserver(Context context, OnKeyEventObserver observer) {
        if (context == null || observer == null) {
            return false;
        }
        synchronized (mKeyEventObservers) {
            final KeyEventObserverRegistration registration = mKeyEventObservers.get(observer);
            if (registration == null) {
                return false;
            }
            final IBinder service = ServiceManager.getService(SHOULDER_PRESSURE_SERVICE);
            if (service == null) {
                return false;
            }
            final Parcel data = Parcel.obtain(service);
            final Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(SHOULDER_PRESSURE_DESCRIPTOR);
                data.writeString(registration.mObserverKey);
                if (!service.transact(TRANSACTION_UNREGISTER_KEY_EVENT_OBSERVER,
                        data, reply, 0)) {
                    return false;
                }
                reply.readException();
                final boolean unregistered = reply.readBoolean();
                if (unregistered) {
                    mKeyEventObservers.remove(observer);
                }
                return unregistered;
            } catch (RemoteException | RuntimeException e) {
                Log.e(TAG, "Unable to unregister key-event observer", e);
                return false;
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    public boolean registerKeyEventInterceptor(Context context, String interceptorKey,
            OnKeyEventObserver observer, ArrayMap<Integer, Integer> configs) {
        if (context == null || TextUtils.isEmpty(interceptorKey) || observer == null
                || configs == null || configs.isEmpty()) {
            Log.e(TAG, "Invalid key-event interceptor registration: " + interceptorKey);
            return false;
        }

        synchronized (mKeyEventInterceptors) {
            if (mKeyEventInterceptors.containsKey(interceptorKey)) {
                Log.e(TAG, "Key-event interceptor is already registered: " + interceptorKey);
                return false;
            }

            final IBinder service = ServiceManager.getService(SHOULDER_PRESSURE_SERVICE);
            if (service == null) {
                Log.e(TAG, "Shoulder-pressure service is unavailable");
                return false;
            }

            final KeyEventObserverDelegate delegate = new KeyEventObserverDelegate(observer);
            final Parcel data = Parcel.obtain(service);
            final Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(SHOULDER_PRESSURE_DESCRIPTOR);
                data.writeString(interceptorKey);
                data.writeStrongBinder(delegate);
                data.writeInt(configs.size());
                for (int i = 0; i < configs.size(); i++) {
                    data.writeInt(configs.keyAt(i));
                    data.writeInt(configs.valueAt(i));
                }
                if (!service.transact(TRANSACTION_REGISTER_KEY_EVENT_INTERCEPTOR,
                        data, reply, 0)) {
                    Log.e(TAG, "Key-event interceptor transaction is unsupported");
                    return false;
                }
                reply.readException();
                final boolean registered = reply.readBoolean();
                if (registered) {
                    mKeyEventInterceptors.put(interceptorKey, delegate);
                }
                return registered;
            } catch (RemoteException | RuntimeException e) {
                Log.e(TAG, "Unable to register key-event interceptor", e);
                return false;
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    public boolean unregisterKeyEventInterceptor(Context context, String interceptorKey,
            OnKeyEventObserver observer) {
        if (context == null || TextUtils.isEmpty(interceptorKey)) {
            return false;
        }

        synchronized (mKeyEventInterceptors) {
            final IBinder service = ServiceManager.getService(SHOULDER_PRESSURE_SERVICE);
            if (service == null) {
                return false;
            }

            final Parcel data = Parcel.obtain(service);
            final Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(SHOULDER_PRESSURE_DESCRIPTOR);
                data.writeString(interceptorKey);
                if (!service.transact(TRANSACTION_UNREGISTER_KEY_EVENT_INTERCEPTOR,
                        data, reply, 0)) {
                    return false;
                }
                reply.readException();
                final boolean unregistered = reply.readBoolean();
                if (unregistered) {
                    mKeyEventInterceptors.remove(interceptorKey);
                }
                return unregistered;
            } catch (RemoteException | RuntimeException e) {
                Log.e(TAG, "Unable to unregister key-event interceptor", e);
                return false;
            } finally {
                reply.recycle();
                data.recycle();
            }
        }
    }

    public int getVersion() {
        return mVersion;
    }

    public interface OnKeyEventObserver {
        void onKeyEvent(KeyEvent event);
    }

    private static final class KeyEventObserverDelegate extends Binder {
        private static final int TRANSACTION_ON_KEY_EVENT = 1;

        private final OnKeyEventObserver mObserver;

        KeyEventObserverDelegate(OnKeyEventObserver observer) {
            mObserver = observer;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code == INTERFACE_TRANSACTION) {
                if (reply != null) {
                    reply.writeString(KEY_EVENT_OBSERVER_DESCRIPTOR);
                }
                return true;
            }
            if (code == TRANSACTION_ON_KEY_EVENT) {
                data.enforceInterface(KEY_EVENT_OBSERVER_DESCRIPTOR);
                final KeyEvent event = data.readTypedObject(KeyEvent.CREATOR);
                data.enforceNoDataAvail();
                if (event != null) {
                    mObserver.onKeyEvent(event);
                }
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    private static final class KeyEventObserverRegistration {
        final String mObserverKey;
        final KeyEventObserverDelegate mDelegate;

        KeyEventObserverRegistration(String observerKey, KeyEventObserverDelegate delegate) {
            mObserverKey = observerKey;
            mDelegate = delegate;
        }
    }
}
