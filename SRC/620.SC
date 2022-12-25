;**
;**	Logics for room 620 -- Sierra's prop room
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 25, 1989
;**


(script#				620)

(public
	rm620				0
)


(define	vRoom							620)
		(define	cKQsign		0)
		(define	cNumbers		1)
		(define	cSalesman	2)

		(define	cTarget		0)
		(define	cTaxi			1)
		(define	cTombstone	2)
		(define	cCauldron	3)
		(define	cClothes		4)
		
		(define	cMacSign		0)


(enum 620										;**	local egoState values
	local620
)


(instance rm620 of Room
	(properties
		picture		620
		east			630
	)


	(method (init)
		(super init)

		(addToPics
			add			atpKQsign,
			add			atpNumbers,
			add			atpSalesman1,
			add			atpSalesman2,
			add			atpSalesman3,
			add			atpSalesman4,
			add			atpTarget1,
			add			atpTarget2,
			add			atpTarget3,
			add			atpTarget4,
			add			atpTaxi,
			add			atpTombstone,
			add			atpCauldron,
			add			atpClothes,
			add			atpMonolithBurger,
			doit,
		)

		(NormalEgo)
		(ego 
			posn   			12 188,
			init,
		)

		(NormalActor aLarry facingEast)
		(aLarry 
			view				vLhunk,
			posn				12 184,
			setMotion		Follow ego 28,
			setCycle			Walk,
			init,
			loop				facingEast,
		)
	)


	(method (newRoom n)
		(super newRoom n)
		(music fade)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'get/flat')
				(Print "Patti! Why would you want to get flat?")
			)

			((Said 'get')
				(Print "This stuff is not why you're here. Keep moving.")
			)

			((Said 'look>')
				(cond
					((Said '/prop')
						(Print "This room is filled with leftover props from
							all the Sierra adventure games.")
					)

					((Said '/flat')
						(Print "Those flats must be title screens from old
							Sierra adventures.")
					)

					((Said '/people, man')
						(Print "Those must be the insurance salesmen from 
							\"Space Quest II: Vohaul's Revenge.\"")
					)

					((Said '/cauldron')
						(Print "That old tub dates from a very early Al Lowe
							game!")
					)

					((Said '/numeral')
						(Print "You wonder how many numbers are in that box!")
					)

					((Said '/clothes')
						(Print "Those were worn by the Murrys in those
							famous \"Manhunter\" games.")
					)

					((Said '/tombstone')
						(Print "\n\n\"Here lies the remains\n
									of the King's Quest IV\n
									programming staff.\n
									1987-1988\"\n\n"
							#mode			teJustCenter
							WIDE
						)
					)

					((Said '/sign, post')
						(Print "That big M doesn't stand for Marriage, Patti!")
					)

					((Said '/taxi, car')
						(Print "So that's how they did that taxi cab scene in
							\"Leisure Suit Larry in the Land of the Lounge 
							Lizards!\"")
						(Print "(Pick up a copy today, at a software store
							near you!)" LOW)
					)

					((Said '[/set, stage, room]')
						(Print "This must be Sierra's prop storage room.
							Look at all the old flats and backdrops in that rack.")
					)
				)
			)
		)
	)
)


(instance atpKQsign of PicView
	(properties
		view					vRoom
;		loop 					0
;		cel					cKQsign
		x						122 
		y						107
		priority				7
		signal				ignrAct
	)
)
(instance atpNumbers of PicView
	(properties
		view					vRoom
;		loop 					0
		cel					cNumbers
		x						177
		y						164
		priority				12
	)
)
(instance atpSalesman1 of PicView
	(properties
		view					vRoom
;		loop 					0
		cel					cSalesman
		x						7
		y						134
		priority				9
	)
)
(instance atpSalesman2 of PicView
	(properties
		view					vRoom
;		loop 					0
		cel					cSalesman
		x						16
		y						142
		priority				10
	)
)
(instance atpSalesman3 of PicView
	(properties
		view					vRoom
;		loop 					0
		cel					cSalesman
		x						10
		y						151
		priority				11
	)
)
(instance atpSalesman4 of PicView
	(properties
		view					vRoom
;		loop 					0
		cel					cSalesman
		x						26
		y						156
		priority				11
	)
)
(instance atpTarget1 of PicView
	(properties
		view					vRoom
		loop 					1
;		cel					cTarget
		x						238 
		y						158
		priority				11
	)
)
(instance atpTarget2 of PicView
	(properties
		view					vRoom
		loop 					1
;		cel					cTarget
		x						241
		y						161
		priority				12
	)
)
(instance atpTarget3 of PicView
	(properties
		view					vRoom
		loop 					1
;		cel					cTarget
		x						245
		y						164
		priority				12
	)
)
(instance atpTarget4 of PicView
	(properties
		view					vRoom
		loop 					1
;		cel					cTarget
		x						278
		y						116
		priority				8
	)
)
(instance atpTaxi of PicView
	(properties
		view					vRoom
		loop 					1
		cel					cTaxi
		x						106
		y						189
		priority				14
		yStep					14
	)
)
(instance atpTombstone of PicView
	(properties
		view					vRoom
		loop 					1
		cel					cTombstone
		x						23
		y						97
		priority				6
	)
)
(instance atpCauldron of PicView
	(properties
		view					vRoom
		loop 					1
		cel					cCauldron
		x						58
		y						128
		priority				9
	)
)
(instance atpClothes of PicView
	(properties
		view					vRoom
		loop 					1
		cel					cClothes
		x						185
		y						137
		priority				9
	)
)
(instance atpMonolithBurger of PicView
	(properties
		view					vRoom
		loop 					2
		cel					cMacSign
		x						297
		y						156
		priority				11
	)
)

(instance aLarry of Actor)

