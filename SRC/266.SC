;**
;**	Logics for room 266 -- big, BIG Tawni
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 21, 1989
;**


(script#				266)

(public
	rm266				0
)



(define	vRoom							266)
	(define	lSwimsuits			0)
		(define	cTop			0)
		(define	cBottom		1)
		(define	cOnePiece	2)


(enum												;**	RoomScript state values
	intro
	sheLooks
	demo1
	demo2
	demo3
)


(local
	seenMsg
)


(instance rm266 of Room
	(properties
		picture		266
	)


	(method (init)

		(super init)

		(self
			setScript		RoomScript
		)

		(if (< filthLevel DIRTY)
			(addToPics
				add 			atpBikiniTop,
				doit,
			)
		)

		(addToPics
			add 				atpBikiniBottom,
			doit,
		)

		(User
			canInput			TRUE,
			canControl 		FALSE,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'look/beach, room')
					(Said 'leave, quit')
					(Said 'leave, exit, quit, walk, go'))
				(Ok)
				(curRoom newRoom 260)
			)

			((Said 'talk, ask, say')
				(Print "Before you speak, you look straight into her eyes...")
				(curRoom newRoom 265)
			)

			((Said 'give')
				(Print "Why not try looking her straight in the eyes first.")
			)

			((Said 'look>')
				(cond
					((Said '/girl, body, tawni')
						(Print "What a gorgeous woman,
							with obviously nothing to hide!")
						(Print "You suddenly forget about Kalalau completely.
							You could learn to enjoy the single life once again!")
					)

					((Said '/tit')
						(if (>= filthLevel DIRTY)
							(Print "There they are!")
						else
							(Print "You think she seemed slow to cover them when
								she stood up!")
						)
					)

					((Said '/ass, bottom')
						(Print "It's barely covered by that string bikini.")
					)

					((Said '/pussy, crotch')
						(Print "There may indeed be faint outlines visible through
							that knit bikini...")
					)

					((Said '/face, eye')
						(Print "Before you speak, you look straight into her eyes...")
						(curRoom newRoom 265)
					)

					((Said '/leg')
						(Print "Her legs are as smooth as a shopping mall's 
							parking lot.")
					)

					((Said '[/room]')
						(Print "Tawni stood up when you introduced yourself to her.
							Perhaps you should talk to her.")
					)
				)
			)

			(else
				(Print "Before you speak, you look straight into her eyes...")
				(curRoom newRoom 265)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(if (not (TstFlg beenIn266))
					(delay 3)
				)
;**					(if demo
;**						(delay 2)
;**						(++ state)
;**					)
			)

			(sheLooks
				(SetFlg beenIn266)
				(Print "WOW! She looks even better vertical!")
				(Print "(Bet she has a great \"horizontal hold!\")" LOW)
			)


;**				(demo1
;**					(Print "Of course, Tawni is just one of the beautiful women
;**						in this game_-- but you didn't think you'd get to see them all
;**						in a free demo, did you?"
;**						#at -1 144 #width 290 #dispose #time 12)
;**					(delay (+ 2 12))
;**				)
;**	
;**				(demo2
;**					(cls)
;**					(curRoom newRoom 395)
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(curRoom newRoom 395)
;**				)
		)
	)
)


(instance atpBikiniTop of PicView
	(properties
		view			vRoom
		loop			lSwimsuits
		cel			cTop
		x				98
		y				56
	)
)
(instance atpBikiniBottom of PicView
	(properties
		view			vRoom
		loop			lSwimsuits
		cel			cBottom
		x				103
		y				91
	)
)

