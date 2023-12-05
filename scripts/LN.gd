extends Node


signal device_token_received(token)
signal enabled
var _ln = null

onready var _analytics := $'/root/analytics' if has_node('/root/analytics') else null

#I did not change the original functions of the plugin, 
#but I modified the function of calling notifications.
#
#There is nothing complicated. In your game code, 
#simply call the desired function and pass the icons, text and color you need to it



#1 Add the plugin itself to the plugins folder.
#2 Enable plugin in export
#3 Give the plugin the necessary permissions (in the export menu) - android.permission.SCHEDULE_EXACT_ALARM android.permission.USE_EXACT_ALARM android.permission.POST_NOTIFICATIONS
#4 Add all the necessary icons to the mipmap (folder) small icon 96x96 large 256x256


var once_message = "Once Notify Message" #Just Once notification title text 
var repeat_message = "Repeat Notify Message" #Just Repeat notification title text 
#The text of your notification. 
#For a more aesthetically pleasing notification, I disabled the long text.
#If you need it, write me, I will do it.


var once_title = "Once Notify Title" #Once Notification title

var repeat_title = "Repeat Notify Title" #Repeat Notification title

var small_icon_name = "small_icon" #The name of the small icon. You can put any name of your icon that is in the mipmap folder

var large_icon_name = "notify_big" #the same as with the small icon

var summary_text = "Notify Summary" #disabled for now, but must be passed when calling the function


func _ready():
	OS.request_permissions() #We ask the device for the necessary permissions.
	pause_mode = Node.PAUSE_MODE_PROCESS
	if Engine.has_singleton("LocalNotification"):
		_ln = Engine.get_singleton("LocalNotification")

func init() -> void:
	if _ln != null:
		_ln.init()

func show_once(): #One-time notification
	if _ln != null:
		var color = "#ffff00"
		_ln.showLocalNotification(once_message, once_title, 8, 1, small_icon_name, large_icon_name, summary_text, color)

func show_repeat(): #Repeat notification
	if _ln != null:
		var color = "#ff00ff"
		_ln.showRepeatingNotification(repeat_message, repeat_title , 60, 2, 60, small_icon_name, large_icon_name, summary_text, color)

func _cancel_notification(tag):
	if _ln != null:
		_ln.cancelLocalNotification(tag)

func cancel(tag: int = 1) -> void:
	if _ln != null:
		_ln.cancelLocalNotification(tag)

func cancel_all() -> void:
	if _ln != null:
		_ln.cancelAllNotifications()

func is_inited() -> bool:
	if _ln != null:
		return _ln.isInited()
	else:
		return false

func is_enabled() -> bool:
	if _ln != null:
		return _ln.isEnabled()
	else:
		return false

func register_remote_notification() -> void:
	if _ln != null:
		_ln.register_remote_notification()

func get_device_token():
	if _ln != null:
		return _ln.get_device_token()
	else:
		return null

func get_notification_data():
	if _ln != null:
		return _ln.get_notification_data()
	else:
		return null

func get_deeplink_action():
	if _ln != null:
		return _ln.get_deeplink_action()
	else:
		return null

func get_deeplink_uri():
	if _ln != null:
		return _ln.get_deeplink_uri()
	else:
		return null

func _on_notifications_enabled() -> void:
	if _analytics != null:
		_analytics.event('notifications_enabled')
	emit_signal('enabled')

func _on_device_token_received(token) -> void:
	#print('on_device_token_received: %s'%var2str(token))
	emit_signal('device_token_received', token)
