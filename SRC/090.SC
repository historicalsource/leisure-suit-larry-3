;**
;**	Logics for room 90 -- the Boss Key
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	September 8, 1989
;**


(script#				90)

(public
	rm90				0
)

(instance rm90 of Room
	(properties
		picture		090
	)
	(method (init)
		(cls)
		(super init)
		(TheMenuBar hide)
		(StatusLine	disable)
		(ego hide)
		(HandsOff)
		(User canInput TRUE)
		(Animate 0)
	)
	(method (handleEvent event)
		(if (or (== (event type) mouseUp) (event claimed)) (return))
		(Print "Sorry, but from here, all you can do is Restart or Restore a
			saved game.")
		(Print "(Hope you didn't get caught!)" LOW)
		(event claimed TRUE)
	)
)

