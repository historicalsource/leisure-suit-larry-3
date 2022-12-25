;**
;**	Logics for room 310 -- Dewey, Cheatum & Howe exterior
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				310)

(public
	rm310				0
)


(define	vRoom							310)
	(define	lSign					0)
	(define	lDoor					1)
	(define	lRoger				2)
		(define	cRoger		0)
		(define	cNoRoger		1)

(local
	seenMsg
	theCounter
	[string 310]
)


(enum 310										;**	local egoState values
	IN_ROOM_310
	local310
)


(instance rm310 of Room
	(properties
		picture		310
		south			220
	)


	(method (init)
		(super init)

		(addToPics
			add 				atpSign,
			doit
		)

		(if ( < lawyerState LSdone)
			(addToPics
				add 				atpRoger,
				doit
			)
		else
			(addToPics
				add 				atpNoRoger,
				doit
			)
		)

		(aDoor				init)

		(self
			setScript 		RoomScript
		)
		(cond
			((== prevRoomNum 320)		 (ego posn 220  88))
			(else								 (ego posn  84 188))
		)

		(NormalEgo)
		(ego					init)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look<down')
				(Print "When you lean over the railing you can see 
					straight down.")
			)

			((and (ego has iKnife)
					(!= ((inventory at iKnife) view) ivSharpKnife)
					(Said 'sharpen/knife'))
				(Print "This area is ALMOST suitable, but the textured concrete
					here is just too rough for your delicate ginsu.")
			)

			((Said 'look>')
				(cond
					((Said '/city, beach, ocean, water')
						(Print "Those lawyers really know how to pick a location!")
					)

					((Said '/sign')
						(Print "You surmise those three large initials comprise 
							this firm's logo.")
					)

					((Said '/plant')
						(Print "There are a few beautiful flowers clinging
							tenaciously to a steep escarpment.")
					)

					((Said '/flower, rock, wall')
						(Print "There are some beautiful flowers here.")
					)

					((Said '[/room]')
						(Print "You are outside the legal offices of 
							Dewey, Cheatem & Howe, the best Corporation of
							Associated Legal Professionals 
							on the island of Nontoonyt!")
						(Print "(Also the ONLY!)" LOW)
					)	
				)
			)	

			((Said 'get/plant')
				(Print "The only plants here that catch your eye are those 
					hanging on that sheer rock cliff.")
			)

			((Said 'get, pick/orchid')
				(Print "While these flowers are beautiful,
					they just don't \"lei\" right!")
			)
		)
	)
)

(instance atpSign of PicView
	(properties
		view					vRoom
		loop					lSign
;		cel					cSign
		x						192
		y						189
		priority				14
;		signal				ignrAct
	)
)

(instance aDoor of AutoDoor 
	(properties
		view					vRoom
		loop					lDoor
		x						220
		y						87
		entranceTo 			320
		doorState 			doorClosed
		cycleSpeed			2
	)							

	(method (init)
		(super init)
		(self
			setPri			4,
		)
	)
)

(instance atpRoger of PicView
	(properties
		view					vRoom
		loop					lRoger
;		cel					cRoger
		x						219
		y						45	
		priority				2
	)
)

(instance atpNoRoger of PicView
	(properties
		view					vRoom
		loop					lRoger
		cel					cNoRoger
		x						219
		y						51
		priority				2
	)
)

