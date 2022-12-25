;**
;**	Logics for Locale 699 -- Sierra region
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**


(script#				699)

(public
	rm699				0
)


(instance rm699 of Locale
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'talk/man, person, woman')
				(Print "No one here is interested in talking to you.")
			)

			((Said 'remove, (take<off)/shoe')
				(Print "You'd love to, but you'd rip your nylons!")
			)

			((Said '(look<up), look[/ceiling]')
				(Print "The ceiling here reflects this building's industrial
					heritage.")
			)

			((Said 'look/building, room')
				(Print "Who decorated these sets, any way?")
			)
		)
	)
)

