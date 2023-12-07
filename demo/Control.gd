extends Control



func _ready():
	OS.request_permissions()


func _on_Button_pressed():
	ln.show_once("Test Once Message","Test Once Title",10,1,"small_icon","notify_big","Test Once Summary","#FF0000")
	print("Button1 pressed!")


func _on_Button2_pressed():
	ln.show_repeat("Test Repeat Message","Test Repeat Title",10,2,60,"new_icon","notify_big","Test Repeat Summary","#0000FF")
	print("Button2 pressed!")


func _on_Button3_pressed():
	ln.show_once("Test Show Message","Test Show Title",10,3,"notify_small","notify_big","Test Show Summary","#00FF00")
	print("Button3 pressed!")
