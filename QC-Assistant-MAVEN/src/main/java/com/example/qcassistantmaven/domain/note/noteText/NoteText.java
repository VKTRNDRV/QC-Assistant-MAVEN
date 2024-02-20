package com.example.qcassistantmaven.domain.note.noteText;

public class NoteText {

    public static final String SIM_TYPE_NOT_MATCHING = "Destination SIM Type does not match Order SIM type (confirm with Senior).";
    public static final String VERIFY_SIM_TYPE = "Verify Destination SIM Type (Unknown Destination).";
    public static final String CONFIRM_INVOICE_APPROVED = "Confirm if Invoice approved before forwarding to ship";


    public static final String ADD_UNKNOWN_DESTINATION = "Unknown Destination - consider adding it to the database.";
    public static final String VERIFY_AFW_CHARGERS = "Verify correct connector type for Android chargers.";
    public static final String VERIFY_CHARGER_COUNT = "Verify correct count of chargers.";
    public static final String VERIFY_PLUG_TYPE = "Verify chargers of plug type %s are present.";
    public static final String VERIFY_UNKNOWN_PLUG_TYPE = "(Unknown Destination) Verify chargers are of appropriate Plug type.";
    public static final String MEDICAL_DVCS_TL_QC = "Orders with Medical Devices require additional QC by Nedko or Asparuh after 2nd.";
    public static final String CONFIRM_IWATCHES_CHARGED = "Verify Apple Watch(es) are charged.";




    public static final String STUDY_CONTAINS_HEADPH_STYLUSES = "Study marked as requiring Headphones and Styluses for Site Devices.";
    public static final String VERIFY_HEADPHONES_PRESENT = "Verify Headphones present in bin.";
    public static final String HEADPHONES_NOT_DETECTED = "Headphones not detected in order (study marked as requiring headphones).";
    public static final String VERIFY_STYLUS_PRESENT = "Verify Stylus(es) present in bin.";
    public static final String STYLUSES_NOT_DETECTED = "Styluses not detected in order (study marked as requiring styluses).";
    public static final String CONFIRM_IF_HEADPH_STYLUSES_NEEDED = "Confirm if headphones and styluses not required for Site Device(s).";
    public static final String IPAD_PATIENT_DEVICE_CASE = "Studies with iPad Patient devices usually use distinct cases for them.";
    public static final String VERIFY_STANDS_PRESENT = "Verify Phone/Tablet Stand(s) present in bin.";
    public static final String VERIFY_SCREEN_PROTECTOR_PRESENT = "Verify Screen protector(s) present in bin.";




    public static final String VERIFY_SERIALS = "Verify actual devices' serials match serials on labels.";
    public static final String VERIFY_DVC_MODELS = "Verify appropriate device models (Destination requires special device models).";
    public static final String CHECK_DESTINATION_FOR_SPECIAL_MODELS = "Check if models appropriate for destination (Unknown Destination).";
    public static final String VERIFY_UNUSED_DEVICES = "Verify devices are unused (Destination requires unused devices).";
    public static final String VERIFY_CE_MARKS = "Verify CE Marks visible/accessible on devices (Turkey).";
    public static final String VERIFY_BOX_SERIALS = "(If devices in boxes) Verify serials on boxes match their respective device.";




    public static final String ADD_UNKNOWN_STUDY = "Unknown Study - consider adding it to the database.";
    public static final String INCLUDE_ABBVIE_DOC = "Verify Device Decontamination Document is included (sponsor Abbvie).";
    public static final String VERIFY_WELCOME_LETTER = "Verify Welcome Letter fields are populated properly (if necessary at all).";
    public static final String SEPARATE_BUILD_DOCS = "Verify all devices have separate build docs.";
    public static final String MULTIPLE_COPIES_DOCS_REQ = "Multiple copies of documents requested.";
    public static final String CHECK_FOR_TRANSLATED_DOCS = "Check Study folder for Translated Documents.";
    public static final String STUDY_CONTAINS_TRANSLATED_DOCS = "Study marked as containing Translated Documents.";
    public static final String CONFIRM_DOCS_PRINTED = "Confirm all requested documents are printed.";
    public static final String CONFIRM_CORRECT_EDGE = "Verify all docs are printed on the correct edge (short/long).";
    public static final String CONFIRM_TRANSLATED_DOCS_LANG = "Confirm any translated docs are printed in the correct language.";
    public static final String INCLUDE_PATIENT_CLOUD_INSERTS = "Verify P. Cloud Insert(s) included for every Site Device w/ the app installed.";
    public static final String INCLUDE_RAVE_ECONSENT_INSERTS = "Verify Rave eConsent Insert(s) included for every Site Device w/ the app installed";
    public static final String PATIENT_CLOUD_INSERT_UNKNOWN = "If Patient Cloud present on Site Devices - include Insert(s).";
    public static final String RAVE_ECONSENT_INSERT_UNKNOWN = "If Rave eConsent present on Site Devices - include Insert(s).";
    public static final String BUILD_DOCS_BY_HAND = "Build docs in orders w/ Medical devices - 100% filled out by hand!!";
    public static final String CONFIRM_MEDABLE_SITE_CARTONS = "Verify Medable carton inserts included for all Site devices.";
    public static final String VERIFY_MODEL_GSG = "Verify the device type/model is present on the GSG(s) (Phone/Tablet or iOS/AFW).";



    public static final String CONFIRM_SITE_PATIENT_LABELS = "Confirm 'Site Use' on Site labels and 'Patient use' on Patient labels.";
    public static final String CONFIRM_LABEL_DETAILS = "Verify site number (if present) and correct Destination on labels.";
    public static final String VERIFY_PATIENT_IPAD_LABEL = "(study uses iPad Patient Devices) Verify Patient Label(s) used on iPad Patient Device(s).";
    public static final String CHECK_FOR_TRANSLATED_LABELS = "Check Study folder for translated labels.";
    public static final String CONTAINS_TRANSLATED_LABELS = "Study contains translated labels.";
    public static final String VERIFY_LABEL_TYPE = "(if transl. labels required) Verify correct label type used (Site/Patient).";
    public static final String ENGLISH_LABELS_OK = "(Unless otherwise requested in order comments) English labels can be used.";
    public static final String SITE_PATIENT_SEP_LABELS = "Study contains separate labels for Site/Patient devices - verify ones used.";
    public static final String CHECK_SEP_SITE_PATIENT_LABELS = "Check Study Folder for separate site/patient labels.";
    public static final String CONFIRM_NO_PRINTING_ERRORS = "Verify no printing errors on the labels (e.g. text overflowing).";
    public static final String SPECIAL_INSTRUCTIONS = "Special Instructions detected in Order Term Comments.";
    public static final String CHECK_NOTE_OTC = "Check NOTE in Order Term Comments";
    public static final String VERIFY_STUDY_NAME_DOCS = "Verify correct Study Name on all documents.";
    public static final String CONFIRM_ASSET_LABEL = "Verify Asset Number labels are present on all devices.";
    public static final String CONFIRM_ASSET_NUM_ON_LABEL = "Confirm Asset Number pasted on Main Medable Label (above device serial).";
    public static final String NO_DOCS_LABELS_UAT = "IQVIA UAT orders have NO study labels and GSGs by default.";
    public static final String VERIFY_UAT_BUILD_DOC = "Verify UAT Build docs printed for devices.";
    public static final String SIM_LABELS = "Verify SIM labels on devices.";
    public static final String VERIFY_STUDY_GSG = "Verify correct study name on GSG(s).";
    public static final String VERIFY_PHONE_GSG = "Verify the Destination's phone number is present on the GSG(s).";





    public static final String VERIFY_P_CLOUD_MULTIUSER_MODE = "Verify Patient Cloud set to Multi-User Mode on Site Devices.";
    public static final String CAREFUL_SIMILAR_STUDY_NAMES = "Double check AW Group Name (Sponsor has similar study names).";
    public static final String HUB_LOG_VF_GLOBAL = "Confirm Hub is logged with VF-Global credentials.";
    public static final String HUB_LOG_SIMON_IOT = "Confirm Hub is logged with SIMON-IOT credentials.";
    public static final String HUB_LOG_NO_SIM = "(No SIMs detected) Confirm Hub is logged with MDS-PC.";
    public static final String EGYPT_NO_SIM_HUB_LOG = "(Dest. Egypt) Check with Senior whether Hub should be logged MDS-PC or VF-Global.";
    public static final String VERIFY_P_CLOUD_SINGLEUSER_MODE_TABLET = "(iPad Patient Devices) Verify Patient Cloud in Single User Mode on Patient Devices.";
    public static final String NO_LANGUAGES_DETECTED = "No requested languages detected, if indeed none - leave devices as per destination's default.";
    public static final String ENGLISH_REQUESTED = "English requested - leave devices in English.";
    public static final String UNKNOWN_DESTINATION = "(Unknown Destination) Careful with languages (may not have been detected).";
    public static final String MULTIPLE_LANGS_REQUESTED = "Multiple requested languages detected - check order comments for their priority.";
    public static final String CHANGE_DEVICE_LANGUAGE = "Set devices in requested language after QC.";
    public static final String UNUSUAL_LANGUAGES_REQUESTED = "Unusual language(s) requested (as per destination) - double check!";
    public static final String VERIFY_VFGO_APN = "Verify iot.simon.vfgo APN has been applied automatically.";
    public static final String VERIFY_SIMON_APN = "Verify iot.simon APN has been applied automatically.";
    public static final String VERIFY_APPS_GREEN_CHECK = "Verify all apps have green checkmarks in 'Apps' section in AirWatch.";
    public static final String UAT_CHECK_APPS = "UAT Order - Verify all assigned Apps have been installed.";
    public static final String CONFIRM_SITE_APPS_INSTALLED = "Confirm all Site apps installed: %s";
    public static final String CONFIRM_PATIENT_APPS_INSTALLED = "Confirm all Patient apps installed: %s";
    public static final String VERIFY_P_CLOUD_UPDATED = "Verify latest version of Patient Cloud installed on all devices.";
    public static final String VERIFY_NO_DECOM_TAG = "Verify none of the devices have 'Decom' Tag in AirWatch.";
    public static final String VERIFY_RETROFIT_TAG = "(study marked as Legacy) Verify all devices have 'Legacy Retrofit' Tag in AW.";
    public static final String VERIFY_LEGACY_APN_TAG = "(study marked as Legacy) Verify all devices have APN Tag (as per SIM) in AW.";
    public static final String IS_SITE_PATIENT_SEPARATED = "Study's AW groups marked as Site-Patient separated.";
    public static final String IS_DESTINATION_SEPARATED = "Study's AW groups marked as Destination separated.";
    public static final String IS_OS_SEPARATED = "Study's AW groups marked as OS separated.";
    public static final String CONFIRM_APPROPRIATE_GROUP = "Confirm devices are located in the appropriate AW group.";
    public static final String VERIFY_HUB_CREDENTIALS = "Verify Hub logged with correct Credentials as per respective AW group.";



    public static final String VERIFY_AUTO_DOWNLOADS = "Verify 'Automatic Downloads' in Play Store Settings is 'ON' or 'Managed by Admin'.";
    public static final String VERIFY_SCREEN_LOCK_NONE = "Verify 'Screen Lock Type' set to 'None' in Settings.";
    public static final String VERIFY_S7_BATTERY = "Verify Galaxy S7 phones are sufficiently charged (battery falls quick).";
    public static final String VERIFY_NO_DUPLICATES_AW = "Verify devices have NO duplicate enrollments in AirWatch.";
    public static final String CAREFUL_UAT_ENVIRONMENT = "Careful with the UAT AirWatch groups.";
    public static final String DUPLICATE_SERIALS = "Duplicate Serials detected - %s";
    public static final String VERIFY_LUNA_STEPS = "(If Scribe app assigned) Perform Luna Steps.";
    public static final String VERIFY_SIMS_ACTIVE = "Verify SIMs are active.";
    public static final String SITE_APPS_CAMERA = "One or more Site apps requires camera - verify camera works on site devices.";
    public static final String PATIENT_APPS_CAMERA = "One or more Patient apps requires camera - verify camera works on patient devices.";
    public static final String VERIFY_SWITCH_TO_MOBILE = "Verify 'Switch to Mobile Data' toggle is ON in settings (use SIM trick if necessary).";
    public static final String VERIFY_APPS_LANGUAGES = "Verify requested language is available (automatically or as an option) on all Apps.";
    public static final String UAT_SIMS_DEACTIVATED = "SIMs in UAT Medable orders are sent deactivated (by default).";
    public static final String OS_APPS_LANGS_MIGHT_DIFFER = "Different languages might be requested for OS/Apps or Site/Patient devices - double check.";
    public static final String CONFIRM_CHINA_GROUP_HONG_KONG = "Confirm if China group is to be used for Hong Kong";
    public static final String CHECK_CHINA_GROUP = "Check if order contains China group";
    public static final String CONTAINS_CHINA_GROUP = "Study contains China group in AW";
    public static final String VERIFY_AFW_UPDATED = "Verify Android devices' OS is updated!";
    public static final String TEST_CAMERA_VOICE = "Test Camera and Voice Recorder.";
    public static final String CONFIRM_BATTERY_BEFORE_ENROLL = "Confirm devices have >40% battery before enrolling";
}
