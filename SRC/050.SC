;**
;**	Script 50 -- DOOR
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	August 7, 1989
;**

;**	Al's first class.	 Handles regular & automatic doors.


(script#				DOOR)


;**	Door states (from "game.sh")
;**	(enum
;**		doorClosed
;**		doorOpening
;**		doorOpen
;**		doorClosing
;**	)


(class Door of Prop
	(properties
		cycleSpeed		1
		entranceTo 		0
		locked 			FALSE
		openSnd 			0
		closeSnd 		0
		doorState 		doorClosed
		doorCtrl 		cBlue
		roomCtrl			cGreen
		doorBlock		cYellow
		code 				0
		illegalBits 	0
		force				0
		notify			0
	)


	(methods
		init
		doit
		open
		close
		cue
	)


	(method (init)
		(if (and prevRoomNum
					(== prevRoomNum entranceTo))
			(= doorState doorOpen)
		)

		(if (== doorState doorClosed)
			(= cel 0)
			(ego observeControl doorBlock)
		else
			(= cel (- (NumCels self) 1))
			(= locked FALSE)
			(ego ignoreControl doorBlock)
		)

		(super init)
		(self
			stopUpd,
			ignoreActors
		)
	)


	(method (doit)
		(super doit)

		(if (!= doorState doorOpen)
			(ego observeControl doorBlock)
		else
			(ego ignoreControl doorBlock)
		)

		(if (and roomCtrl
					entranceTo
					(& (ego onControl) roomCtrl))
			(curRoom newRoom entranceTo)
		)
	)


	(method (open)
		(cond
			((and	(not force)
					(!= (ego onControl origin) doorCtrl))
				(NotClose)
			)
			(locked
				(Print "You can't. It's locked.")
			)
			((or	(== doorState doorOpening)
					(== doorState doorOpen))
				(ItIs)
			)
			(else
				(= doorState doorOpening)
				(self setCycle EndLoop self)
				(if openSnd
					(openSnd play)
				)
			)
		)
	)


	(method (close)
		(cond
			((and (not force)
					(!= (ego onControl origin) doorCtrl))
				(NotClose)
			)
			(locked
				(Print "You can't. It's locked.")
			)
			((or	(== doorState doorClosing)
					(== doorState doorClosed))
				(ItIs)
			)
			((& (ego onControl) doorBlock)
				(if ( > filthLevel CLEAN)
					(Print "Get your fat ass out of the doorway first.")
				else
					(Print "Please move out of the doorway first.")
				)
			)
			(else
				(= doorState doorClosing)
				(self setCycle BegLoop self)
				(if closeSnd
					(closeSnd play)
				)
			)
		)
	)


	(method (cue)
		(= doorState
			(if (== doorState doorClosing)
				doorClosed
			else
				doorOpen
			)
		)

		(if (== doorState doorOpen)
			(ego ignoreControl doorBlock)
		else
			(ego observeControl doorBlock)
		)

		(self stopUpd)
		(if notify
			(notify cue)
			(= notify 0)
		)
	)
)

