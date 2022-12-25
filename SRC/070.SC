;**
;**	Logics for Locale 70 -- general answers for Anygirl's face closeup
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 17, 1989
;**


(script#				70)

(public
	rm70				0
)


(instance rm70 of Locale
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'kiss')
					(Said '/kiss')
					(Said '//kiss')
					(Said '/nut')
					(Said '//nut')
					(Said '/tit')
					(Said '//tit'))
				(Print "That's ONE thing you're hoping for!")
			)

			((Said '/ass')
				(Print "\"Round, firm, fully-packed!
					So easy on the draw!\"")
			)

			((Said '/hand')
				(Print "Her hands are lovely,
					but it's not her hands you're interested in!")
			)

			((Said '/body')
				(Print "Another perfect 10!
					How can all the women on this island be so beautiful?")
			)

			((Said '/feet')
				(Print "You dream of starting there and working your way
					to the top!")
			)

			((Said '/hair')
				(Print "You wonder if it smells as good as it looks!")
			)

			((Said '/ear')
				(Print "You can picture yourself whispering sweet
					nothings into that beautiful, shell-shaped orifice!")
			)

			((Said '/nose')
				(Print "It busily exchanges moisture-laden carbon dioxide for
					fresh oxygen-enriched atmosphere.")
				(Print "(In other words, she's still breathing, Larry!)" LOW)
			)

			((Said '/mouth, teeth')
				(Print "You imagine your tongue winding its way around
					those pearly whites!")
			)

			((Said '/eye')
				(Print "You stare deeply into her eyes.
					She loves it.")
			)

			((Said '/tongue')
				(Print "You love the way her tongue moves around her lips.")
			)
		)
	)
)

