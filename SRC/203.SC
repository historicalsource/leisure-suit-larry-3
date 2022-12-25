;**
;**	Logics for room 203 -- Plaque
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 26, 1989
;**


(script#				203)

(public
	rm203				0
	ShadowPrint		1
	PrintPlaque		2
)

(procedure
	ShadowPrint
	PrintPlaque		
)


;(define	MASH_18						9)


(local
	[string 30]
	letteringColor
)


(instance rm203 of Room
	(properties
		picture		203
	)

	(method (init)
		(super init)
		(SetFlg noCursor)
		(= oldEgoState egoState)
		(= egoState curRoomNum)

		(if ( > (Graph GDetect) 4)
			(= letteringColor yellow)
		else
			(= letteringColor white)
		)
		(User canControl FALSE)
		(User canInput    TRUE)

		(self
			setScript 		RoomScript
		)
		(PrintPlaque)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (== (GameIsRestarting) RESTORING)
			(PrintPlaque)
		)
	)

	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'look/away, room')
					(Said 'look<quit, stop')
					(Said 'quit, stop/look')
					(Said 'look<quit, stop/plaque')
					(Said 'leave, go, exit, quit, stop'))
				(Ok)
				(= egoState oldEgoState)
				(curRoom newRoom 200)
			)

			((Said 'look/plaque')
				(Print "On this site, the great hero of our people\n\n
					LARRY LAFFER\n\n
					singlehandedly saved our island from\n
					our mortal enemy, the evil Dr. Nonookee."
					BIG
					#mode			teJustCenter
				)
			)

			((Said 'look/face, head')
				(Print "Yep, it's Larry; Larry Laffer.")
			)

			((Said 'look')
				(Print "You are reading a beautiful bronze plaque
					commemorating the previous adventures of
					Larry; Larry Laffer.")
				(if (not egoIsPatti)
					(Print "Your heart fills with pride,
						but also with longing for those simple days of yesteryear.")
				)
			)
		)
	)
)


;**		The procedure "ShadowPrint" expects an x/y location; a bright
;**	color (above 7); a font; and a pointer to the string to be printed.

(procedure (ShadowPrint x y c f ptr)
	(Display ptr #p_at x 		y 			#p_font f #p_color c)
	(Display ptr #p_at (+ x 1) (+ y 1) 	#p_font f #p_color (- c 8))
)


(procedure (PrintPlaque)
	(ShadowPrint  54 105 letteringColor SERIF_12 (Format @string
									"On this site, the great hero of our people"))
	(ShadowPrint  90 120 letteringColor MASH_18  (Format @string
									"LARRY LAFFER"))
	(ShadowPrint  56 140 letteringColor SERIF_12 (Format @string
									"singlehandedly saved our island from"))
	(ShadowPrint  49 155 letteringColor SERIF_12 (Format @string
									"our mortal enemy, the evil Dr. Nonookee."))
)
