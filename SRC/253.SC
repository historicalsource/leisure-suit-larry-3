;**
;**	Logics for room 253 -- Restroom
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				253)

(public
	rm253				0
)


(define	vRoom							253)
	(define	lDoorIn				0)
	(define	lDoorOut				1)
	(define	lSoap					2)
		(define	cSoap			0)
	(define	lAlsHead				3)
	(define	lAlsFeet				4)
	(define	lBillTalking		5)
	(define	lBillyBoy 			6)

(define	vLroom				(+ 1 253))
	(define	lBendOverSink		0)
	(define	lDrinkingWater		1)
	(define	lBottleToSink		2)
	(define	lFillingBottle		3)
	(define	lLgettingSoap		2)
	(define	lLsoapToPocket		3)

(define	vProom				(+ 2 253))

(define	vJodi							256)
	(define	lStripping			4)

(define	vSoundsBy					257)
	(define	lSoundsBy			0)
	(define	lRobAtesalp			1)


(define	sWaterFaucet				253)


(define	outsideLeft 		(& (ego onControl) cBlue))
(define	outsideMiddle 		(& (ego onControl) cGreen))
(define	outsideRight 		(& (ego onControl) cCyan))
(define	insideLeft	 		(& (ego onControl) cRed))
(define	insideRight	 		(& (ego onControl) cBrown))
(define	nearSink				(& (ego onControl) cWhite))
(define	nearTrash			(& (ego onControl) cGrey))


(define	jodiY 	 					143)


(enum												;**	RoomScript state values
	intro
	getSoap
	atSoap
	gotTheSoap
	soapDone
	drinkWater
	atSink
	drinkingWater
	standUpAgain
	drinkDone
	fillBottle
	bottleToSink
	fillingBottle
	standUpWithBottle
	bottleFilled
	putOnSkirt
	skirtIsOn
	takeOffSkirt
	tossGrassSkirt
)


(enum												;**	JodiScript state values
	jodiInit
	bringJodiIn
	atHerBooth
	inBooth
	showOffThoseBoobs
	shesNaked
	leaveBooth
	leaveRoom
	pastBill
	jodiGone
)


(enum												;**	BillScript state values
	billInit
	billStart
	lookAtJodi
	looking
	backToAl
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	clearCredits
)


(local
	sinkX
	sinkY
)


(synonyms
	(sink fountain faucet handle )
	(trash garbage can )
)


(instance rm253 of Room
	(properties
		picture		253
		west			250
	)

	(method (init)
		(Load VIEW vLroom)

		(if egoIsPatti
			(= sinkX 45)
			(= sinkY 139)
		else
			(= sinkX 43)
			(= sinkY 137)
		)
		(if (and (ego has iGrass)
					(== ((Inventory at iGrass) view) ivGrassSkirt))
			(Load VIEW vLgrassSkirt)
			(Load VIEW vLundressing)
			(Load VIEW ivGrassSkirt)
		)

		(if (ego has iBottle)
			(Load VIEW iBottle)
		)

		(if (not (TstFlg sawCredits253))
			(aCredit1			init)
			(aCredit2			init)
		)

		(if (or	(TstFlg forceAtest)
					(and	(not (Random 0 4))
							(> machineSpeed pc8088)
							(not egoIsPatti)
							(not (TstFlg seenJodiStrip))
							(TstFlg sawCredits253)))
			(aJodi				init)
			(aBill				init)
			(alsHead				init)
			(alsFeet				init)
		)

		(super init)

		(self
			setScript 		RoomScript
		)

		(if (InRoom iSoap)
			(aSoap				init)
		)

		(aDoor
			setPri				10,
			ignoreActors,
			init
		)

		(ego
			posn 				1 171
		)
		(NormalEgo)
		(ego			 		init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(cond
			((and	outsideLeft
					(or	(< (ego heading)  90)
							(> (ego heading) 269)))
				(aDoor
					doorCtrl		cBlue,
					loop			lDoorIn,
				)
			)

			((and	insideLeft
					(> (ego heading)  89)
					(< (ego heading) 271))
				(aDoor
					doorCtrl 	cRed,
					loop			lDoorOut,
				)
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'drink')
					(Said 'turn/sink, water')
					(Said 'get/drink/water')
					(Said 'get/drink'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearSink)
						(Print "You can't reach the sink from here.")
					)
					(else
						(self changeState drinkWater)
					)
				)
			)

			((Said 'piss')
				(cond
					((not insideLeft)
						(Print "Not here! Can't you find someplace more modest?")
					)
					(else
						(Print "Ahhhhhh!")
					)
				)
			)

			((or	(Said 'fill/bottle')
					(Said 'put/water/bottle')
					(Said 'get/water'))
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearSink)
						(Print "You can't reach the sink from here.")
					)
					((not (ego has iBottle))
						(Print "You have no way to carry any water.")
					)
					((!= ((Inventory at iBottle) view) ivEmptyBottle)
						(Print "Your bottle is currently full.")
					)
					(else
						(self changeState fillBottle)
					)
				)
			)

			((Said 'get/soap')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (InRoom iSoap))	;**	default to curRoomNum
						(AlreadyTook)
					)
					((not nearSink)
						(NotClose)
					)
					(else
						(self changeState getSoap)
					)
				)
			)

			((or	(Said 'wear, (change<into), (put<on)/grass, skirt')
			 		(Said '(change<from, out), (take<off), remove/suit, clothes'))
				(cond
					(insideRight
						(Print "This cubicle has no door.
							You are far too modest to change clothes here.")
					)
					((not insideLeft)
						(Print "Not here! Can't you find someplace more modest?")
					)
					((not (ego has iGrass))
						(Print "What do you want to wear?")
					)
					((!= ((Inventory at iGrass) view) ivGrassSkirt)
						(Print "How can you wear a clump of grass?")
					)
					((and (< filthLevel DIRTY)
							(aDoor cel))
						(Print "Close the door!")
					)
					((== egoState WEARING_GRASS_SKIRT)
						(self changeState takeOffSkirt)
					)
					(else
						(self changeState putOnSkirt)
					)
				)
			)

			((or	(Said 'wear, (change<into), (put<on)/suit, clothes')
			 		(Said '(change<from, out), (take<off), remove/grass, skirt'))
				(cond
					((== egoState NORMAL)
						(Print "What do you want to wear?")
					)
					(insideRight
						(Print "This cubicle has no door.
							You are far too modest to change clothes here.")
					)
					((not insideLeft)
						(Print "Not here! Can't you find someplace more modest?")
					)
					((!= egoState WEARING_GRASS_SKIRT)
						(Print "How can you do that?")
					)
					((and (< filthLevel DIRTY)
							(aDoor cel))
						(Print "Close the door!")
					)
					(else
						(self changeState takeOffSkirt)
					)
				)
			)

			((Said 'wash/face, body, me, hand')
				(if (or	(TstFlg needsShower)
							(TstFlg needsSoap))
					(Print "You need more than just a hand basin to get rid of
						that workout aroma!")
				else
					(Print "You would, but why bother?")
				)
			)

			((Said 'look>')
				(cond
					((Said '/cabana, building')
						(Print "The cabana has three cubicles
							(some of which have doors),
							and a sink with running water at its left end.")
					)

					((Said '/sink')
						(Printf "A public sink is mounted on the side of the
							cabana.%s"
							(if (InRoom iSoap)
								{ A bar of soap hangs over the sink,
								suspended by a rope looped over a nail.}
							else {}))
					)

					((and	(InRoom iSoap)
							(Said '/soap, rope'))
						(Print "There's a bar of soap
							that's hanging from a rope
							that's looped over a nail
							that's driven into a board
							that's attached to the wall
							that's at the end of the cabana.")
					)

					((and (not (ego has iBottle))
							(Said '/water'))
						(Print "There is a water faucet on the sink.
							It may work.")
					)

					((Said '/wall, graffiti')	;**	spell-checked June 12, 1989
						(if insideLeft
							(Print "On the wall is scrawled the word \"ZAX\"
								which you think you've heard before,
								you're just not sure where.")
						else
							(Print "\"Two Guys from Clovis\"")
						)
					)

					((Said '/zax')
						(Print "Graffiti is not important in this game.")
					)

					((Said '/nail, board, rope')
						(Print "Hey, come on. That was a JOKE!")
					)

					((Said '/seat, hole')
						(if insideLeft
							(Print "YUCK!")
						else
							(NotClose)
						)
					)

					((Said '/garbage')
						(if nearTrash
							(Print "Believing yourself to be playing a previous
								Leisure Suit Larry game, 
								you dig through the all the garbage in the can,
								only to discover...")
							(Print "...a postcard saying, 
								\"The weather is here; wish you were beautiful.\"
								You return it to the trash.")
						else
							(NotClose)
						)
					)

					((Said '/showroom, casino')
						(Print "The casino is located just to the south of this
							clearing.")
					)

					((Said '/door')
						(cond
							(insideLeft
								(Print "The door closed behind you.")
							)
							(outsideLeft
								(Print "The left cubicle is unoccupied at the
									moment. Just walk inside.")
							)
							(outsideMiddle
								(Print "This door is stuck. You cannot get inside.")
							)
							(outsideRight
								(Print "This cubicle has no door.
									You have no desire to use it!")
							)
							(else
								(NotClose)
							)
						)
					)

					((Said '[/room]')
						(Print "The casino's changing cabana
							(which bears an amazing resemblance to an outhouse)
							is nestled in a grove of trees.")
					)
				)
			)

			((Said '/door')
				(Print "The left door is the only one that is available to you.
					The center door seems stuck and the right door is missing.")
			)

			((Said '/trash')
				(Print "Stop thinking about garbage and get your mind back 
					in the gutter!")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**		Get the bar of soap from the nail over the sink on the wall
			;**	at the end of the cabana.

			(getSoap
				(HandsOff)
				(Ok)
				(ego
					illegalBits		0,
					setMotion		MoveTo sinkX sinkY self,
				)
			)

			(atSoap
				(ego
					view 				vLroom ,
					loop 				lLgettingSoap,
					cel 				0,
					setCycle			EndLoop self,
				)
			)

			(gotTheSoap
				(aSoap	dispose)
				(ego
					get 				iSoap,
					loop				lLsoapToPocket,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(soapDone
				(NormalEgo facingEast)
				(theGame changeScore 12)
				(Print "You grab the unusually-shaped
					\"Soap-On-A-Rope\" that hangs above the sink.")
			)


			;**	get a drink of water from the sink

			(drinkWater
				(HandsOff)
				(Ok)
				(ego
					illegalBits		0,
					setMotion		MoveTo sinkX sinkY self,
				)
			)

			(atSink
				(soundFX
					number			sWaterFaucet,
					loop				11,
					play,
				)
				(ego
					view 				(+ vLroom egoIsPatti),
					loop 				lBendOverSink,
					cel 				0,
					setCycle			EndLoop self,
				)
			)

			(drinkingWater
				(ego
					loop				lDrinkingWater,
					setCycle			Forward,
				)
				(delay 5)
			)

			(standUpAgain
				(ego
					loop				lBendOverSink,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(drinkDone
				(soundFX				stop)
				(if (not (TstFlg drankSinkWater))
					(SetFlg drankSinkWater)
					(theGame changeScore 2)
				)
				(Print "Ahhhhh." HIGH)
				(NormalEgo facingEast)
			)


			;**	fill the bottle with water

			(fillBottle
				(HandsOff)
				(Ok)
				(ego
					illegalBits		0,
					setMotion		MoveTo sinkX sinkY self,
				)
			)

			(bottleToSink
				(soundFX
					number			sWaterFaucet,
					loop				1,
					play,
				)
				(ego
					view 				(+ vLroom egoIsPatti),
					loop 				lBottleToSink,
					cel 				0,
					setCycle			EndLoop self,
				)
				(Print "You place your bottle under the tap,
					while carefully considering the level of
					carcinogenic compounds therein."
					#icon iBottle 0 0
				)
			)

			(fillingBottle
				(ego
					loop				lFillingBottle,
					setCycle			Forward,
				)
				(delay 5)
			)

			(standUpWithBottle
				(ego
					loop				lBottleToSink,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(bottleFilled
				(soundFX				stop)
				((Inventory at iBottle)
					view 				ivWaterBottle,
				)
				(Format ((Inventory at iBottle) name) "Bottle of Water")
				(if (not (TstFlg scoredWater))
					(SetFlg scoredWater)
					(theGame changeScore 37)
				)
				(NormalEgo facingEast)
			)


			;**	Larry puts on the grass skirt

			(putOnSkirt
				(HandsOff)
				(Ok)
				(if (not (TstFlg woreGrassSkirt))
					(SetFlg woreGrassSkirt)
					(theGame changeScore 10)
				)
				(ego
					illegalBits		0,
					cycleSpeed		1,
					view				vLundressing,
					loop				2,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(skirtIsOn
				(= normalEgoView vLgrassSkirt)
				(= egoState WEARING_GRASS_SKIRT)
				(NormalEgo facingNorth)
				(Print "You remove your leisure suit and hide it inside the
					stall while slipping into the grass skirt.
					Boy, do you look like a native now!"
					#icon ivGrassSkirt 0 0
				)
			)


			;**	Larry taking off grass skirt & returning to suit

			(takeOffSkirt
				(Ok)
				(HandsOff)
				(ego
					illegalBits		0,
					cycleSpeed		1,
					view				vLundressing,
					loop				2,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(tossGrassSkirt
				(= normalEgoView vLarry)
				(= egoState NORMAL)
				(NormalEgo facingNorth)

				(if (ego has iMoney)			;**	He has no further use for skirt.
					(PutInRoom iGrass LIMBO)
					(Print "Since you have no further use for the silly grass skirt, 
						you toss it through a hole conveniently placed in the floor 
						(you presume for just this purpose).")
				)
			)
		)
	)
)


(instance aDoor of AutoDoor
	(properties
		view				vRoom
		loop				lDoorIn
		x					93
		y					126
		doorCtrl 		cBlue
		doorBlock		cYellow
		roomCtrl			FALSE
	)
)
(instance aSoap of View
	(properties
		view				vRoom
		loop				lSoap
;		cel				cSoap
		x					64
		y					111
	)
	(method (init)
		(super init)
		(self
			setPri			ltGreen,
			ignoreActors,
			stopUpd,
		)
	)
)


(instance BillScript of Script
	(method (handleEvent event)
		(cond
			((super handleEvent event))

			((Said 'talk/bill, man')
				(Print "Bill ignores you (as usual).")
			)

			((Said 'talk/al')
				(Print "Al is too busy working on something else to talk to you.")
			)

			((Said 'look/al')
				(Print "You couldn't bear the thought!")
			)

			((Said 'look/bill')
				(Print "He's giving Al more verbal abuse.")
			)

			((Said 'look/man')
				(Print "It's William Skirvin, world-famous artist talking
					to Al Lowe, a ne'r-do-well game designer.")
			)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(billInit
				(aBill
					setCel				0,
				)
				(delay (Random 2 9))
			)

			(billStart
				(aBill
					setLoop				lBillTalking,
					setCycle				Forward,
				)
				(= state readyToStartOver)
				(delay (Random 2 5))
			)


			;**	Bill looks around

			(lookAtJodi
				(aBill
					setLoop				lBillyBoy,
					cel					0,
					setCycle				EndLoop self,
				)
				(delay 0)
			)

			(looking
				(Print "\"Wow!\" says Bill. \"Take a look at that bikini!\"")
				(Print "\"Open the door a little, will you, Bill?\" responds Al.")
				(delay 3)
			)

			(backToAl
				(aBill
					setCycle				BegLoop self,
				)
				(= state readyToStartOver)
			)
		)
	)
)


(instance aJodi of Actor
	(properties
		view				vJodi
		x					-30
		y					jodiY
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			setCycle		Walk,
			setScript	JodiScript,
		)
	)
)
(instance JodiScript of Script
	(method (handleEvent event)
		(cond
			((super handleEvent event))

			((Said '/body')
				(Print "(Pant, pant, pant.)" LOW)
			)

			((Said '/girl, blond')
				(cond
					(( < (aJodi y) 0)
						(Print "Where?")
					)
					((and	( > state inBooth)
							( < state leaveBooth))
						(Print "She's undressing!")
					)
					(else
						(Print "What a body!")
					)
				)
			)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(jodiInit
				(delay (Random 5 10))
			)

			(bringJodiIn
				(aJodi
					setMotion		MoveTo 184 jodiY self,
				)
			)

			(atHerBooth
				(aJodi
					setMotion		MoveTo 184 137 self,
				)
			)

			(inBooth
				(delay 3)
			)

			(showOffThoseBoobs
				(aJodi
					loop				lStripping,
					cel				0,
					cycleSpeed		2,
					setCycle			EndLoop self,
				)
			)

			(shesNaked
				(SetFlg seenJodiStrip)
				(if (or insideRight outsideRight)
					(Print "\"Are you enjoying the view, buddy?\"")
				)
				(delay 3)
			)

			(leaveBooth
				(aJodi
					cycleSpeed		0,
					setCycle			Walk,
					setMotion		MoveTo 184 jodiY self,
				)
			)

			(leaveRoom
				(aJodi
					setMotion		MoveTo (aBill x) jodiY self
				)
			)

			(pastBill
				(BillScript changeState lookAtJodi)
				(aJodi
					setMotion		MoveTo -10 jodiY self
				)
			)

			(jodiGone
				(aJodi				dispose)
				(self					dispose)
			)
		)
	)
)


(define	billyBoyX					   160)
(define	billyBoyY 						140)

(instance aBill of Prop
	(properties
		view				vRoom
		loop				lBillTalking
		x	 				billyBoyX
		y	 				billyBoyY
	)
	(method (init)
		(super init)
		(self
			setPri		10,
			setScript	BillScript,
			stopUpd,
		)
	)
)


(define	alX					138)
(define	alHeadY				096)
(define	alFeetY				132)

(instance alsHead of View
	(properties
		view				vRoom
		loop				lAlsHead
		x					alX
		y					alHeadY
		priority			6
		signal  			(| stopUpdOn staticView ignrAct)
	)
)
(instance alsFeet of View
	(properties
		view				vRoom
		loop				lAlsFeet
		x					alX
		y					alFeetY
		priority			9
		signal  			(| stopUpdOn staticView ignrAct)
	)
)


(instance aCredit1 of Prop
	(properties
		view				vSoundsBy
;		loop				lSoundsBy
;		cel				0
		x					288
		y					131
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view				vSoundsBy
		loop				lRobAtesalp
;		cel				0
		x					288
		y					154
		cycleSpeed		1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(doCredits
				(delay 4)
			)

			(doCredits1
				(aCredit1 setCycle EndLoop)
				(= cycles 16)
			)

			(doCredits2
				(aCredit2 setCycle EndLoop)
				(= cycles 22)
			)

			(doCredits3
				(SetFlg sawCredits253)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)
