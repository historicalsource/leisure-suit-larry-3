;**
;**	Logics for Locale 399 -- Fat City
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 30, 1990
;**


(script#				399)

(public
	rm399				0
)


;(define	sFatCityRegion				399)


(instance rm399 of Locale
	(method (init)
		(super init)

		(if (not (OneOf prevRoomNum FAT_CITY_ROOMS))
			(music
				number		sFatCityRegion,
				loop			bgMusicLoops,
				play,
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'look<down')
					(Said '/floor'))
				(Print "Fat City's floor is covered with dried sweat droplets.")
			)

			((or	(Said 'look/ceiling')
					(Said 'look[<up'))
				(Print "Fat City's ceiling is the same color as
					their old gym towels.")
			)

			((Said '/wall')
				(Print "The walls here are nearly vertical.")
			)

			((Said '/door')
				(Printf "Yes, Fat City does have doors, %s." egoName)
			)
		)
	)
)


