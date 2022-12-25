;**
;**	Logics for Locale 80 -- Clerk (egoIsLarry)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				80)

(public
	rm80				0
)


(instance rm80 of Locale
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said '/hair')
					(Print "You see a man with dark hair.")
			)

			((Said '/patti')
				(Print "He can't help you with Patti.")
				(if (>= filthLevel DIRTY)
					(Print "(...although he has had her!)" LOW)
				)
			)

			((Said 'give')
				(Printf "The clerk is not interested in anything you have
					to offer, %s." egoName)
			)

			((or	(Said '//counter, desk')
					(Said '/counter, desk'))
				(Print "There is a clerk behind the counter.")
			)

			((Said '/man')
				(Print "He seems to really enjoy his job.")
				(Print "(You remember feeling that way once!)" LOW)
			)

			((Said '/face')
					(Print "He's an ugly dude.")
			)
		)
	)
)



