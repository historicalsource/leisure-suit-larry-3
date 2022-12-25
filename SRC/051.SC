;**
;**	Script 51 -- AUTODOOR
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 7, 1989
;**

;**	Al's first class.	 Handles regular & automatic doors.


(script#				AUTODOOR)


;**	Door states (from "game.sh")
;**	(enum
;**		doorClosed
;**		doorOpening
;**		doorOpen
;**		doorClosing
;**	)


(class AutoDoor of Door						;**	properties same as regular doors
	(methods
		open
		close
	)

	(method (init)
		(super init)
	)

	(method (doit)
		(super doit)

		(if code
			(if (code doit self)
				(self open)
			else
				(self close)
			)
		else
			(if (& (ego onControl) doorCtrl)
				(self open)
			else
				(self close)
			)
		)
	)


	(method (open)
		(if (and
				(not locked)
				(!= doorState doorOpening)
				(!= doorState doorOpen))
			(= doorState doorOpening)
			(self setCycle EndLoop self)
			(if openSnd
				(openSnd play)
			)
		)
	)


	(method (close)
		(if (and
				(!= doorState doorClosing)
				(!= doorState doorClosed))
			(= doorState doorClosing)
			(self setCycle BegLoop self)
			(if closeSnd
				(closeSnd play)
			)
		)
	)
)

