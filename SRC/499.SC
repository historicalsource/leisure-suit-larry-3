;**
;**	Logics for Locale 499 -- Casino Interior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 25, 1989
;**


(script#				499)

(public
	rm499				0
)


;(define	sCasinoRegion				499)


(instance rm499 of Locale
	(method (init)
		(super init)

		(if (not (OneOf prevRoomNum CASINO_ROOMS))
			(music
				number		sCasinoRegion,
				loop			bgMusicLoops,
				play,
			)
		)
	)


	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'talk/man, person, woman')
				(Print "No one here is interested in talking to you.")
			)

			((Said 'remove, (take<off)/shoe')
				(Printf "You'd love to, but you'd %s!"
					(if egoIsPatti
						{rip your nylons}
					else
						{get those white socks dirty}
					)
				)
			)

			((Said 'gamble')
				(Print "Nah, you'd just lose!")
			)

			((Said '/picture, painting')
				(Print "This place is ostentatious enough for even YOUR taste.")
			)

			((Said '(look<up), look[/ceiling]')
				(Print "The ceiling in the casino is just as garish
					as the floor.")
			)

			((Said '/casino, gambling, building')
				(Print "You have no desire to gamble after your experiences
					in \"Leisure Suit Larry in the Land of the Lounge Lizards.\"")
			)

			((Said 'look>')
				(cond
					((Said '/building, casino, room')
						(Print "You are so sorry you didn't hire this
							interior decorator when you built your home!")
					)

					((Said '/traffic, city')
						(Print "How nice it is that automobiles were never allowed
							here on the island!
							Everyone does aerobic walking all the time!")
					)

					((Said '/ground')
						(Print "This carpet was handmade by island virgins.")
						(Print "(That's why it's now old and threadbare!)" LOW)
					)
				)
			)

			((Said '/casino')
				(Print "Isn't this a fun place?")
			)
		)
	)
)

