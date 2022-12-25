;**
;**	Logics for Locale 81 -- Clerk (egoIsPatti)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	June 14, 1989
;**


(script#				81)

(public
	rm81				0
)


(instance rm81 of Locale
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said '/hair')
					(Print "You'd like to run your fingers thru his hair!")
			)

			((Said '/larry')
				(Print "The clerk doesn't know where Larry is any more than
					you do, Patti!")
			)

			((Said 'give')
				(Printf "The clerk is not interested in anything you have
					to offer, %s." egoName)
			)

			((or	(Said '//counter, desk')
					(Said '/counter, desk'))
				(Print "There is a clerk behind the counter.")
			)

			((Said '/face')
					(Print "You remember his lips against your's.")
			)
		)
	)
)




