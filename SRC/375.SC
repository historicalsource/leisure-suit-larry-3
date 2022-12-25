;**
;**	Logics for room 375 -- Fat City's shower room
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 12, 1989
;**


(script#				375)

(public
	rm375				0
)


(define	vRoom 						375)
	(define	lDrain				0)
	(define	lWater1				1)
	(define	lWater2				2)
	(define	lWater3				1)
	(define	lWater4				2)

(define	vLpic375						376)
(define	vLpic376						377)
(define	vLpic375modest				378)


(define	sShower						375)


(define	nearFaucet			(& (ego onControl) cBlue))
(define	gettingWet			(& (ego onControl) cGreen))


(instance rm375 of Room
	(properties
		picture		375
		horizon		1
		south			370
	)

	(method (init)
		(if (ego has iSoap)
			(Load VIEW iSoap)
		)

		(aWater1	init)
		(aWater2	init)
		(aWater3	init)
		(aWater4	init)
		(aDrain	init)

		(if (TstFlg showerRunning)
			(self picture 376)
			(Load PICTURE 375)
			(Load VIEW vLpic375)
			(soundFX
				number		sShower,
				loop			bgMusicLoops,
				play,
			)
		else
			(self picture 375)
			(Load PICTURE 376)
			(Load VIEW vLpic376)
			(Load SOUND sShower)
			(aWater1	hide)
			(aWater2	hide)
			(aWater3	hide)
			(aWater4	hide)
			(aDrain	hide)
		)

		(super init)

		(self
			setScript 		RoomScript,
		)

		(NormalEgo)
		(ego
			view				(cond	((TstFlg showerRunning)		vLpic376)
										((>= filthLevel DIRTY)		vLpic375)
										(else								vLpic375modest)),
			posn 				53 176,
			setPri 			11,
			setStep			5 5,
			init,
		)
	)

	(method (newRoom n)
		(soundFX fade)
		(if (InRoom iTowel 375)
			(ego get iTowel)
			(Print "You grab your towel from the hook by the door.")
		)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (and gettingWet 
					(TstFlg showerRunning))
			(SetFlg wetBody)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'shampoo')
				(Print "Ok. Your hair is clean.")
			)

			((or	(Said 'fondle/self')
					(Said 'masturbate'))
				(Ok)
				(Print "(That was fast!)" LOW)
			)

			((Said 'stop, (turn<off)/shower, faucet, knob, handle, water')
				(cond
					((not (TstFlg showerRunning))
						(ItIs)
					)
					((not nearFaucet)
						(Print "The faucet is near the top of the screen.")
					)
					(else
						(Ok)
						(ego view	(if (>= filthLevel DIRTY)		vLpic375
										else									vLpic375modest))
						(ClrFlg showerRunning)
						(curRoom drawPic 375 DISSOLVE)
						(aWater1	hide)
						(aWater2	hide)
						(aWater3	hide)
						(aWater4	hide)
						(aDrain	hide)
						(soundFX stop)
					)
				)
			)

			((or	(Said 'shower, bathe')
					(Said 'take/shower')
					(Said 'use, (turn<on)/shower, faucet, knob, handle, water'))
				(cond
					((TstFlg showerRunning)
						(ItIs)
					)
					((not nearFaucet)
						(Print "The faucet is near the top of the screen.")
					)
					(else
						(Ok)
						(ego view vLpic376)
						(SetFlg showerRunning)
						(curRoom drawPic 376 DISSOLVE)

						(if (> machineSpeed pc8088)
							(aWater1	show)
							(aWater2	show)
							(aWater3	show)
							(aWater4	show)
						)

						(aDrain	show)
						(soundFX
							number		sShower,
							loop			bgMusicLoops,
							play,
						)
					)
				)
			)

			((Said 'rinse')
				(cond
					((not (TstFlg showerRunning))
						(Print "You may want to turn on the shower first.")
					)
					((not gettingWet)
						(Print "Shouldn't you be in the water?")
					)
					(else
						(Ok)
						(Print "You rinse your body in the sensually-warm
							running water.")
					)
				)
			)

			((or	(Said 'wash')
					(Said 'wash, use/soap'))
				(cond
					((not (TstFlg showerRunning))
						(Print "You may want to turn on the shower first.")
					)
					((not gettingWet)
						(Print "Shouldn't you be in the water?")
					)
					((not (ego has iSoap))
						(Print "You rub your hands all over yourself in an 
							excellent imitation of a bar of soap.
							Too bad you don't have one.")
						(Print "(Bar of soap, that is!)" LOW)
					)
					(else
						(Ok)
						(ClrFlg needsShower)
						(ClrFlg needsSoap)
						(theGame changeScore 60)
						(ego put iSoap LIMBO)
						(Print "You were so wise to bring your \"Soap-On-A-Rope\"
							into the shower with you."
							#icon iSoap 0 0
						)
						(Print "You carefully rub it over every part of your
							body.")
						(if (>= filthLevel DIRTY)
							(Print "...some parts longer than others!")
						)
						(Print "Soon, it's all gone!")
					)
				)
			)

			((and (not (ego has iSoap))
					(Said 'get/soap'))
				(Print "Well, look at that!
					Somebody used up all the soap and left none for you!")
				(Print "(Where could you find soap on this island?)" LOW)
			)

			((Said 'look>')
				(cond
					((Said '/faucet, handle, knob')
						(if (TstFlg showerRunning)
							(Print "It's \"on.\"")
						else
							(Print "It's \"off.\"")
						)
					)

					((Said '/drain')
						(if (TstFlg showerRunning)
							(Printf "Look! The water is going down the drain in a
								%sclockwise direction!"
								(if (Random 0 1) {counter-} else {}))
						else
							(Print "It looks grate!")
						)
					)

					((Said '[/floor, shower, room]')
						(Print "The shower room has a slippery tile floor.")
					)
				)
			)
		)
	)
)


(instance aWater1 of Prop
	(properties
		view					vRoom
		loop					lWater1
		cel					1
		x						148 
		y						 91
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)

(instance aWater2 of Prop
	(properties
		view					vRoom
		loop					lWater2
		cel					2
		x						178 
		y						 61
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)

(instance aWater3 of Prop
	(properties
		view					vRoom
		loop					lWater3
		cel					3
		x						170 
		y						 92
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)

(instance aWater4 of Prop
	(properties
		view					vRoom
		loop					lWater4
		cel					4
		x						128 
		y						 84
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)


(instance aDrain of Prop
	(properties
		view					vRoom
		loop					lDrain
		x						148
		y						91
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward ,
			ignoreActors,
		)
	)
)

