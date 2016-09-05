package ntv.upgrade.medicalcenters.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by xeros on 9/2/2016.
 */

public class NotificationRequest {
    /**
     * Notification Identifiers
     */
    public static final int NOTIFICATION_ID_TOAST = 101;
    public static final int NOTIFICATION_ID_ALERT = 102;
    public static final int NOTIFICATION_ID_ALERT_QUESTION = 103;
    public static final int NOTIFICATION_ID_NOTIFICATION = 104;

    /**
     * Notification Actions
     */
    public static final String NOTIFICATION_ACTION_OK = "OK";
    public static final String NOTIFICATION_ACTION_YES = "YES";
    public static final String NOTIFICATION_ACTION_NO = "NO";

    /**
     * Member Variables
     */
    private final Context mContext;
    private final int mId;
    private final int mIcon;
    private final String mTittle;
    private final String mMessage;
    private final Runnable mPositiveAction;
    private final Runnable mNegativeAction;
    private final boolean mIsCancelable;

    // Constructor
    NotificationRequest(Context context, int id, int icon, String tittle, String message,
                        Runnable positiveAction, Runnable negativeAction, boolean isCancelable) {
        this.mContext = context;
        this.mId = id;
        this.mIcon = icon;
        this.mTittle = tittle;
        this.mMessage = message;
        this.mPositiveAction = positiveAction;
        this.mNegativeAction = negativeAction;
        this.mIsCancelable = isCancelable;
    }

    /**
     * Chooses the method to be executed based on the defined ID.
     *
     * @return wetter notification was posted successfully.
     */
    public boolean post() {

        switch (mId) {
            case NOTIFICATION_ID_TOAST:
                return showToast();

            case NOTIFICATION_ID_ALERT:
                return showAlert();

            case NOTIFICATION_ID_ALERT_QUESTION:
                return showAlertQuestion();

            case NOTIFICATION_ID_NOTIFICATION:
                return showNotification();

            default:
                return false;
        }
    }

    private boolean showToast() {

        Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG)
                .show();
        return true;
    }

    private boolean showAlert() {
        new AlertDialog.Builder(mContext)
                .setCancelable(mIsCancelable)
                .setIcon(mIcon).setTitle(mTittle)
                .setMessage(mMessage)
                .setPositiveButton(NOTIFICATION_ACTION_OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mPositiveAction.run();
                                dialog.dismiss();
                            }
                        }).show();
        return false;
    }

    private boolean showAlertQuestion() {
        new AlertDialog.Builder(mContext)
                .setCancelable(mIsCancelable)
                .setIcon(mIcon)
                .setTitle(mTittle)
                .setMessage(mMessage)
                .setPositiveButton(NOTIFICATION_ACTION_YES,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mPositiveAction.run();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(NOTIFICATION_ACTION_NO,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mNegativeAction.run();
                                dialog.dismiss();
                            }
                        }).show();
        return false;
    }

    private boolean showNotification() {
        /*
        // Gets list of attractions for current area from the database
        UtilityService.getAttractions(mContext, mArea.getId());

        // The intent to trigger when the notification is tapped
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                new Intent(mContext, ActivityMain.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // The bitmap to display when the notification is expanded
        Bitmap bitmap = BitmapFactory.decodeResource(
                mContext.getResources(),
                R.drawable.santo_domingo_zon_galleryfull);

        // Construct the main notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .setBigContentTitle(mArea.getName())
                        .setSummaryText(mContext.getString(R.string.nearby_attraction))
                )
                .setContentTitle(mArea.getName())
                .setContentText(mArea.getStringType())
                .setSmallIcon(mArea.getIcon())
                .setContentIntent(pendingIntent)
                .setColor(mContext.getResources().getColor(R.color.colorPrimary
                        getTheme()
                ))
                .setCategory(Notification.CATEGORY_RECOMMENDATION)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Trigger the notification
        NotificationManagerCompat.from(mContext).notify(
                NOTIFICATION_ID_ENTER_GEOLOCATION, builder.build());
*/
        return false;
    }

    /**
     * NotificationRequest Builder Inner Class. Creates a notification request
     * based on given parameters
     */
    public static final class Builder {
        private Context context = null;
        private int id;
        private int icon;
        private String tittle = null;
        private String message = null;
        private Runnable positiveAction = null;
        private Runnable negativeAction = null;
        private boolean isCancelable;

        // Default Constructor
        public Builder() {
        }

        /**
         * Defines launcher context for the notification.
         *
         * @param context Is used to define the context from
         *                where the notification was triggered
         * @return builder
         */
        public NotificationRequest.Builder setContext(Context context) {
            NotificationTools.zzb(context, "context can\'t be null");
            this.context = context;
            return this;
        }

        /**
         * Defines the type of notification to be triggered. id needs to
         * be set to one of the NOTIFICATION_ID values on the NotificationRequest class
         *
         * @param id Notification type/style
         * @return builder
         */
        public NotificationRequest.Builder setId(int id) {
            if (id >= 100 && id <= 104) {
                this.id = id;
            } else {
                throw new IllegalArgumentException("Invalid id, Refer to id documentation for more details");
            }
            return this;
        }

        /**
         * Icon needs to be set if @param id was set to:
         * NOTIFICATION_ID_ALERT or
         * NOTIFICATION_ID_ALERT_QUESTION or
         * NOTIFICATION_ID_NOTIFICATION
         *
         * @param icon is used to set icon image for the notification
         * @return builder
         */
        public NotificationRequest.Builder setIcon(int icon) {
            if (id == NOTIFICATION_ID_ALERT ||
                    id == NOTIFICATION_ID_ALERT_QUESTION ||
                    id == NOTIFICATION_ID_NOTIFICATION) {
                this.icon = icon;
            } else {
                throw new IllegalArgumentException("id set does not support an icon. Please set a different notification id.");
            }
            return this;
        }

        /**
         * Tittle needs to be set if @param id was set to:
         * NOTIFICATION_ID_ALERT or
         * NOTIFICATION_ID_ALERT_QUESTION or
         * NOTIFICATION_ID_NOTIFICATION
         *
         * @param tittle is used to set tittle value for the notification
         * @return builder
         */
        public NotificationRequest.Builder setTittle(String tittle) {
            if (id == NOTIFICATION_ID_ALERT ||
                    id == NOTIFICATION_ID_ALERT_QUESTION ||
                    id == NOTIFICATION_ID_NOTIFICATION) {
                NotificationTools.zzb(tittle, "tittle can\'t be null");
                this.tittle = tittle;
            } else {
                throw new IllegalArgumentException("id set does not support a tittle. Please set a different notification id.");
            }
            return this;
        }

        /**
         * Message needs to be set if @param id was set to:
         * NOTIFICATION_ID_TOAST or
         * NOTIFICATION_ID_ALERT or
         * NOTIFICATION_ID_ALERT_QUESTION or
         * NOTIFICATION_ID_NOTIFICATION
         *
         * @param message is used to set tittle value for the notification
         * @return builder
         */
        public NotificationRequest.Builder setMessage(String message) {
            if (id == NOTIFICATION_ID_TOAST ||
                    id == NOTIFICATION_ID_ALERT ||
                    id == NOTIFICATION_ID_ALERT_QUESTION ||
                    id == NOTIFICATION_ID_NOTIFICATION) {
                NotificationTools.zzb(tittle, "area can\'t be null");
                this.message = message;
            } else {
                throw new IllegalArgumentException("id set does not support a message. Please set a different notification id.");
            }
            return this;
        }

        public NotificationRequest.Builder setPositiveAction(Runnable action) {
            if (id == NOTIFICATION_ID_ALERT ||
                    id == NOTIFICATION_ID_ALERT_QUESTION) {
                NotificationTools.zzb(action, "Positive action can\'t be null");
                this.positiveAction = action;
            } else {
                throw new IllegalArgumentException("id set does not support a positive action. Please set a different notification id.");
            }
            return this;
        }

        public NotificationRequest.Builder setNegativeAction(Runnable action) {
            if (id == NOTIFICATION_ID_ALERT_QUESTION) {
                NotificationTools.zzb(action, "Negative action can\'t be null");
                this.negativeAction = action;
            } else {
                throw new IllegalArgumentException("id set does not support a negative action. Please set a different notification id.");
            }
            return this;
        }

        public NotificationRequest.Builder isCancelable(boolean isCancelable) {
            if (id == NOTIFICATION_ID_ALERT ||
                    id == NOTIFICATION_ID_ALERT_QUESTION) {
                this.isCancelable = isCancelable;
            } else {
                throw new IllegalArgumentException("id set does not support a cancelable option. Please set a different notification id.");
            }
            return this;
        }

        /**
         * Builds the notification request
         *
         * @return NotificationRequest
         */
        public NotificationRequest build() {
            return new NotificationRequest(
                    this.context,
                    this.id,
                    this.icon,
                    this.tittle,
                    this.message,
                    this.positiveAction,
                    this.negativeAction,
                    this.isCancelable);
        }

    }
}

