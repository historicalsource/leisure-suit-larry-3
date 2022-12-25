;**
;**	Logics for room 610 -- falling into PQ1
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				610)

(public
	rm610				0
)


(define	vRoom							610)
	(define	lHouses				0)
	(define	lHoleCover			1)
	(define	lATPs					2)
		(define	cStudioC		0)
		(define	cPQ			1)
	(define	lTowersEL			3)
	(define	lTowers				4)
	(define	lTowersATP			5)
		(define	towerX	170)
		(define	towerY	109)

(define	vLroom						611)
	(define	lGodzilla			0)
	(define	lSparking			1)
	(define	lHeadOverHeelsEL	2)
	(define	lFlat2standingEL	3)

(define	vPQmen						613)
(define	vPoliceCars					614)
		(define	carX		145)
		(define	carY		129)

(define	vCars							615)
(define	vCamera						616)
(define	vLweightless				631)
	(define	lLtumbling			1)

;(define	vPbikini						801)

;(define	vPfallingIn					804)
	(define	lPfallingIn			0)
	(define	lFloor2standingEL	1)


;(define	sFalling						004)
;(define	sEgoStunned					005)
;(define	sNearDanger					006)
;(define	sHitBottom					012)
(define	sPQ							610)
(define	sSparks						611)
;(define	sSierraRegion				699)


(enum												;**	Man2Script state values
	Man2Init
	Man2Start
	Man2Middle
	Man2Done
)


(enum												;**	RoomScript state values
	intro
	meanwhile
	PQquiet
	PQmusic
	PQlights
	PQcamera
	PQaction
	startScene
	PSmusicDone
	pattiAtHole
	pattiOnFloor
	hereComesLarry
	LatTable
	niceLanding
	LatTowers
	wireDown
	moveLtoHole
	LatHole
	LthruHole
	cooCoo
	entrance
	staggering
	LtoFeet
	Lstanding
	RUok
	heyPatti
	PisUp
	iGuess
	LfollowsP
	PinPlace
	whereAre
	goodQuestion
)


(instance rm610 of Room
	(properties
		picture		610
		east			620
	)


	(method (init)
		(HandsOff)
		(SetFlg cantSave)
		(SetFlg noCursor)
		(SetFlg preventAutoSave)

		(= oldGameSpeed (theGame setSpeed 6))

		;**	DEBUGGING CODE:
		;**	
									(= egoIsPatti TRUE)
									(= normalEgoView vPbikini)
									(= egoName (Format @egoNameBuffer "Patti"))
		;**	
		;**	

		(super		init)

		(addToPics
			add 				atpSign, 
			add 				atpStencil1, 
			add 				atpStencil2, 
			add 				atpHouse1,
			add 				atpHouse2,
			add 				atpHouse3,
			add 				atpHouse4,
			add 				atpHouse5,
			add 				atpHouse6,
			add 				atpHouse7,
			doit,
		)

		(self
			setScript 		RoomScript
		)

		(HandsOff)
		(Load SOUND sFalling)
		(Load SOUND sEgoStunned)
		(Load SOUND sNearDanger)
		(Load SOUND sHitBottom)
		(Load SOUND sSparks)
		(Load SOUND sSierraRegion)

		(aMan1				init)
		(aMan2				init)
		(aCar1				init)
		(aCar2				init)
		(aTowers				init)
		(aHole 				init)
		(aCamera				init)
		(ego									;**	ego is Patti
			illegalBits		0,
			posn				171 -50,
			view				vPfallingIn,
			setLoop			lPfallingIn,
			setPri			14,
			setCycle			Walk,
			setStep			1 11,
			init,
		)
		(aLarry								;**	just following along now
			ignoreHorizon,
			illegalBits		0,
			posn 				73 -70,
			view				vLweightless,
			setLoop			lLtumbling,
			setPri			9,
			setCycle			Walk,
			setStep			1 11,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (& (ego onControl) cBlue)		(curRoom newRoom 620))
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look>')
				(cond
					((Said '/door, wall, sign, exit')
						(Print "A sign on the rear wall of the studio mentions
							that Studio C is off to the upper right.")
					)

					((Said '/tombstone')
						(Print "It says, \"Here lies Al Lowe,
							`the Pride of St. Louis.'\"")
					)

					((Said '[/set, table, stage, room]')
						(Print "You are backstage at the Sierra Studios.
							From the looks of things, they are filming another
							in the series of \"Police Quest\" games.")
						(Print "(Or they were until you two \"dropped in!\")" LOW)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 5 green)

		(switch (= state newState)

			(intro
				(if ( < prevRoomNum curRoomNum)
					(delay 3)
				)
			)

			(meanwhile
				(Print "Meanwhile, back at the Sierra Studios,
					workers are busy filming yet another exciting episode in
					the never-ending saga of Sonny Bonds and the
					citizens of Lytton, California, U.S.A." 
						#at		10 40
						#width	290
				)
				(delay 3)
			)

			(PQquiet
				(Print "\"Quiet on the set, please.\"")
				(delay 2)
			)

			(PQmusic
				(Print "\"Music!\"")
				(music
					number			sPQ,
					loop				1,
					play				self,		;**	end of PQ music will cue falling
				)
				(delay 3)
			)

			(PQlights
				(Print "\"Lights!\""
					#time			2
				)
				(delay 3)
			)

			(PQcamera
				(Print "\"Camera!\""
					#time			2
				)
				(aCamera setCycle Forward)
				(delay 3)
			)

			(PQaction
				(Print "\"Action!\""
					#time			2
				)
				(delay 3)
			)

			(startScene
				(aCamera 
					setMotion		MoveTo 0 0,
					setCycle			Forward,
				)
				(aCar1 
					setMotion		MoveTo 0 carY,
				)
				(aMan1 
					setMotion		MoveTo 0 (+ carY 14) ,
				)
				(Man2Script cue)
			)


			;**	We get to this spot only when the PQ music tells us it's done:

			(PSmusicDone
				(cls)
				(Print "\"Craig! Look out! Get outta here!!\"")
				(music
					number			sFalling,
					loop				1,
					play ,			
				)
				(aMan1
					moveSpeed		0,
					cycleSpeed		0,
					setStep			5 5,
					setMotion		MoveTo -20 (aMan1 y) ,
				)
				(aMan2
					moveSpeed		0,
					cycleSpeed		0,
					setStep			5 5,
					setMotion		MoveTo (aMan2 x) 222,
				)
				(aCar1				stopUpd)
				(aCar2				stopUpd)
				(aCamera				stopUpd)
				(ego
					setMotion		MoveTo 171 119 self,
				)
			)

			(pattiAtHole
				(aHole				dispose)
				(music
					number			sHitBottom,
					loop				1,
					play ,
				)
				(ego 	
					posn				171 143,
					setPri			12,
					setMotion		MoveTo 175 186 self,
				)
			)

			(pattiOnFloor
				(ego  
					loop				lFloor2standingEL,
					cel				0,
					stopUpd,
				)
				(RedrawCast)
				(music
					number			sEgoStunned,
					loop				1,
					play ,
				)
				(ShakeScreen 2 SHAKE_UP_DOWN)
				(= cycles 33)
			)

			(hereComesLarry
				(cls)
				(music
					number			sFalling,
					loop				1,
					play ,			
				)
				(aLarry
					setMotion		MoveTo 73 73 self,
				)
			)

			(LatTable
				(aLarry
					posn				73 89,
					view				vLroom,
					setLoop			lGodzilla,
					setStep			2 1,
				)
				(music
					number			sHitBottom,
					loop				1,
					play ,
				)
				(RedrawCast)
				(ShakeScreen 1 SHAKE_UP_DOWN)
				(Print "UGH!" HIGH #dispose)
				(= cycles 11)
			)

			(niceLanding
				(music
					number			sNearDanger,
					loop				FOREVER,
					play ,			
				)
				(cls)
				(Print "Watch out, Godzilla Larry, or you'll destroy 
					the City of Lytton!!" 
					#at 				148  22
					#dispose)
				(aLarry
					setMotion		MoveTo 169 109 self,
				)
			)

			(LatTowers
				(aMan1				dispose)
				(aMan2				dispose)
				(music
					number			sSparks,
					loop				FOREVER,
					play ,			
				)
				(aLarry
					setLoop			lSparking,
					setCycle			Forward,
				)
				(aTowers
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(wireDown
				(aTowers
					setLoop			lTowers,
					cycleSpeed		0,
					setCycle			Forward,
				)
				(= cycles 33)
			)

			(moveLtoHole
				(cls)
				(aLarry
					setMotion		MoveTo 167 110 self,
				)
			)

			(LatHole
				(music
					number			sFalling,
					loop				1,
					play ,			
				)
				(aLarry
					setPri			10,
					setLoop			lHeadOverHeelsEL,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(LthruHole
				(aLarry
					posn				165 180,
					setPri			12,
					setLoop			lFlat2standingEL,
					cel				0,
				)
				(= cycles 15)
			)

			(cooCoo
				(music
					number			sEgoStunned,
					loop				1,
					play 				self,
				)							 				
			)

			(entrance
				(Print "Wow! Some guys really know how to make an entrance!")
				(delay 3)
			)

			(staggering
				(Print "Staggering to his feet, 
					Larry surveys your new surroundings.")
				(= cycles 11)
			)

			(LtoFeet
				(aLarry
					setCycle			EndLoop self,
				)
			)

			(Lstanding
				(NormalActor aLarry facingSouth vLhunk)
				(aLarry
					illegalBits		0,
				)
				(= cycles 22)
			)

			(RUok
				(Print "Larry spots you lying motionless on the floor beneath
					the miniature city of Lytton.
					\"Patti! Are you ok?\" he cries.")
				(delay 2)
			)

			(heyPatti
				(ego
					setCycle			EndLoop self,
				)
			)

			(PisUp
				(NormalEgo facingNorth normalEgoView)
				(ego
					illegalBits		0,
				)
				(HandsOff)
				(= cycles 22)
			)

			(iGuess
				(Print "\"I guess so,\" you reply.
					\"But if I keep doing my own stunts like this,
					I'm going to end up flat-chested!\"")
				(Print "\"Come on, Larry,\" you say,
					\"let's get out from under here.\"")
				(ego					
					setMotion		MoveTo 308 183 self,
				)
				(= cycles 11)
			)

			(LfollowsP
				(aLarry
					setMotion		MoveTo 276 185 self,
				)
			)

			(PinPlace
				(ego
					loop				facingWest,
				)
			)

			(whereAre
				(ClrFlg cantSave)
				(ClrFlg noCursor)
				(ClrFlg preventAutoSave)
				(theGame setSpeed oldGameSpeed)

				(NormalEgo facingWest)
				(ego
					setPri			7,
				)
				(aLarry
					setPri			7,
					illegalBits		cLtWhite,
					setMotion		Follow ego 28,
				)
				(Print "\"But what is this place, Larry?\" you ask.")
				(delay 2)
			)

			(goodQuestion
				(Print "\"Good question,\" he replies.
					\"Although it does seem familiar!\"")
				(Print "\"You take the lead, Patti. Let's explore!\"")
				(music
					number			sSierraRegion,
				 	loop				bgMusicLoops,
					play ,			
				)
			)
		)
	)
)


(instance atpSign of PicView
	(properties
		view				vRoom
		loop				lATPs
		cel				0
		x	 				285
		y					46
	)
)
(instance atpStencil1 of PicView
	(properties
		view				vRoom
		loop				lATPs
		cel				1
		x	 				0
		y					143
		priority			15
		signal			ignrAct
	)
)
(instance atpStencil2 of PicView
	(properties
		view				vRoom
		loop				lATPs
		cel				1
		x	 				173
		y					142
		priority			15
		signal			ignrAct
	)
)
(instance atpHouse1 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				0
		x	 				91
		y					126
		priority			9
	)
)
(instance atpHouse2 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				1
		x	 				5
		y					104
		priority			9
	)
)
(instance atpHouse3 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				1
		x	 				41
		y					126
		priority			9
	)
)
(instance atpHouse4 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				3
		x	 				57
		y					99
		priority			9
	)
)
(instance atpHouse5 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				4
		x	 				95
		y					97
		priority			9
	)
)
(instance atpHouse6 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				4
		x	 				128
		y					116
		priority			9
	)
)
(instance atpHouse7 of PicView
	(properties
		view				vRoom
		loop				lHouses
		cel				3
		x	 				123
		y					109
		priority			9
	)
)


(instance aHole of View
	(properties
		view				vRoom
		loop				lHoleCover
		x					156
		y					120
	)
	(method (init)
		(super init)
		(self
			setPri		13,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aCamera of Actor
	(properties
		view				vCamera
		x					carX
;		y					0
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			setStep			1 1,
		)
	)
)
(instance aCar1 of Actor
	(properties
		view				vPoliceCars
		x					carX
		y					carY 
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			9,
			setStep			1 1,
			setLoop			facingWest,
			setCycle			Walk,
		)
	)
)
(instance aMan1 of Actor
	(properties
		view				vPQmen
		x					carX
		y					(+ carY 14)
		illegalBits		0
		cycleSpeed		2
		moveSpeed		2
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setLoop			facingWest,
			setCycle			Walk,
		)
	)
)


(instance aCar2 of Actor
	(properties
		view				vPoliceCars
		x					130
		y					127
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			9,
			setStep			1 1,
			setLoop			facingSouth,
			setCycle			Walk,
		)
	)
)
(instance aMan2 of Actor
	(properties
		view				vPQmen
		x					130
		y					(+ 127 14)
		illegalBits		0
		cycleSpeed		1
		moveSpeed		1
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setStep			2 2,
			setLoop			facingNorth,
			setCycle			Walk,
			setScript		Man2Script
		)
	)
)

(instance Man2Script of Script
	(method (changeState newState)		
		(ShowState self newState 1 green)

		(switch (= state newState)

			(Man2Init
			)



			(Man2Start
				(aCar2 
					setMotion		MoveTo 88 102,
				)
				(aMan2 
					setMotion		MoveTo 88 (+ 102 14) self,
				)
			)

			(Man2Middle
				(aCar2 
					setMotion		MoveTo 130 127 ,
				)
				(aMan2 
					setLoop			facingSouth,
					setMotion		MoveTo 130 (+ 127 14) ,
				)
			)

			(Man2Done
			)
		)
	)
)

(instance aTowers of Prop
	(properties
		view				vRoom
		loop				lTowersEL
;		cel				0
		x					towerX
		y					towerY
	)

	(method (init)
		(super init)
		(self
			setPri			10,
			stopUpd,
			ignoreActors,
		)
	)
)

(instance aLarry of Actor)

