;**
;**	Logics for room 520 -- beside the waterfall
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 18, 1989
;**


(script#				520)

(public
	rm520				0
)


;(define	vStream						520)
	(define	lRock1				0)
	(define	lRock2				1)
	(define	lRock3				2)

(define	vPtyingHose					521)
		(define	tiePosn		213 104)
		(define	rockPosn		212  71)
	(define	lRemovingHose		0)
	(define	lTieToRock			1)
	(define	lTieToSelf			2)
	(define	lOverTheSide		3)

;(define	vPdrinkingStream			511)
	(define	lGoingDown			0)
	(define	lDrinking			1)

;(define	vPdrowning					812)


;(define	sNearDanger					006)


(define	nearTitRock		(& (ego onControl )			cRed))
(define	nearWater		(& (ego onControl )			cLtBlue))
(define	onWater			(& (ego onControl origin)	cLtBlue))
(define	walkingOnAir	(& (ego onControl )			cBlue))


(enum												;**	RoomScript state values
	intro
	startDrowning
	drowningAndSplashing
	splashingAndDrowning
	atLipOfFalls
	overTheFalls
	deadFromWaterfall
	startDrinking
	drinkWater
	allDone
	tinyDelay
	doneDrinking
	startFalling
	falling2
	gonnaDieIn525
	tegethoff
	standBackUp
	timing
	moveToTitRock
	tie2rock
	PolishComedian
	tie2self
	youBroughtHer
	goOverEdge
	goDown
	youTimer
	changeRooms
)


(local
	drownCount
	[string  50]
	[string2 22]
)


(instance rm520 of Room
	(properties
		picture		520
		east			523
		south			510
	)


	(method (init)
		(Load VIEW vPdrinkingStream)
		(Load VIEW vPdrowning)
		(Load VIEW vPfalling)
		(Load VIEW vPtyingHose)

		(Load SOUND sFalling)
		(Load SOUND sNearDanger)

		(if (ego has iPantyhose)
			(Load VIEW iPantyhose)
		)

		(super init)

		(self
			setScript 		RoomScript
		)

		(aRock1				init)
		(aRock2				init)
		(aRock3				init)
		(aRock4				init)
		(aRock5				init)

		(cond
			((== egoState DROWNING_IN_RIVER)
				(ego posn 69 188)
				(= egoState DROWNING)
				(RoomScript changeState startDrowning)
			)

			((== prevRoomNum 523)			;**	coming in from right
				 (NormalEgo facingLeft)
			)

			(else
				 (ego posn 181 188)
				 (NormalEgo facingNorth)
			)
		)

		(ego					init)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(cond
			((and onWater
					(== egoState NORMAL))
				(self changeState startDrowning)
			)

			((and	(== egoState DROWNING)
					( < 8 (++ drownCount)))
				(= drownCount 0)
				(ego
					;**	Pick loop 0 or 1 if big Patti, or Loop 2 or 3 if far away.
					setLoop			(+ (Random 0 1) (* 2 (< (ego y) 107))),
				)
			)

			((and walkingOnAir
					(== egoState NORMAL))
				(self changeState startFalling)
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'get/drink, water')
					(Said 'drink')
					(Said 'drink/water'))
				(cond
					((TstFlg drankRiverWater)
						(Print "Your thirst is sufficiently quenched now, Patti.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearWater)
						(NotClose)
					)
					(else
					  	(self changeState startDrinking)
					)
				)
			)

			((Said 'use, tie/bra')
				(cond
					((not (ego has iBra))
						(DontHave)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Print "You briefly consider using your bra to lower
							yourself over the cliff,
							but decide it's just too small to do any good.
							Perhaps it will be useful later.")
					)
				)
			)

			((Said 'use/hose')
				(cond
					((not (ego has iPantyhose))
						(DontHave)
					)
					(else
						(Print "A good idea; but how would you use them?")
					)
				)
			)

			((Said 'remove, (off<take), (take<off)/hose')
				(cond
					((InRoom iPantyhose 484)
						(Print "A good idea.
							How you wished you had worn yours
							when you left your apartment!")
					)
					((InRoom iPantyhose LIMBO)
						(DontHave)
					)
					((TstFlg pantyhoseOff)
						(Print "You already did.
							Now do something with it!")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(self changeState tegethoff)
					)
				)
			)

			((Said '(put<on), wear/hose')
				(cond
					((InRoom iPantyhose 484)
						(Print "A good idea.
							How you wished you had worn yours
							when you left your apartment!")
					)
					((InRoom iPantyhose LIMBO)
						(DontHave)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (TstFlg pantyhoseOff))
						(Print "You ARE wearing them!")
					)
					(else
						(Print "O.K. They're on again. Now what?")
						(theGame changeScore -15)
						(ClrFlg pantyhoseOff)
					)
				)
			)

			((Said 'tie/hose>')
				(cond
					((not (ego has iPantyhose))
						(Print "A good idea, but you forgot to wear your pantyhose
							when you got dressed back at the casino.")
					)
					((not (TstFlg pantyhoseOff))
						(Print "You would, but you're still wearing them!")
					)
					((Said '//noword')
						(Print "To what?")
					)
					((not (Said '//rock'))
						(Print "That just won't work.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearTitRock)
						(Print "There IS a rock here that is suitable for your
							purposes; you are just unable to reach it from here.")
					)
					(else
						(self changeState moveToTitRock)
					)
				)
				(event claimed TRUE)
			)

			((Said 'look>')
				(cond
					((Said '/tree')
						(Print "These trees bring back fond memories of your youth
							spent back on the plains of Mora, Minnesota.")
					)

					((Said '/river')
						(Print "The river here sweeps down from the mountains,
							swiftly disappearing into the jungle ahead.
							Vicious rapids roil its surface.")
					)

					((Said '/rock, tit')
						(if nearTitRock
							(Print "The rock near you has a cylindrical top,
								strongly resembling a boat tie down.")
						else
							(Print "The centuries of lava flows have left unusual
								rock formations everywhere on the island.")
						)
					)

					((Said '/ledge, cliff, canyon')
						(Print "It's a long way down from here!
							Evidently the bamboo forest was all uphill,
							as you are now considerably higher in altitude.")
					)

					((Said '/waterfall, rapid, water')
						(Print "The river here boils and bubbles as it prepares
							to drop over the cliff and out of sight to the valley
							below.")
						(Print "(You wonder why there are no souvenir stands
							here.)" LOW)
					)

					((Said '[/room]')
						(Print "You are near the edge of a dramatic cliff.
							The river beside you hurls itself over the edge,
							forming a spectacular waterfall and
							disappearing into the canyon below.")
					)
				)
			)

			((Said '(up<climb), climb/rock, arch')
				(Print "There is no way you could climb up that arch!
					It will never help you, Patti.")
			)

			((or	(Said '(climb, go)<(down, over)')
					(Said 'lower/me'))
				(Print "Yes, but how?")
			)

			((Said 'climb')
				(Print "You MIGHT be able to climb down this cliff.
					Try moving closer to the edge.")
				(Print "(Will you really \"fall\" for this old joke?)" LOW)
			)

			((Said 'pull, hold, get/vine, rope')
				(Print "The flowering vines are not sturdy enough to support you.")
			)

			((Said 'get, use/tree')
				(Print "The trees here are not close enough to the edge of the
					cliff to aid your descent.")
			)

			((Said '/plant')
				(Print "The plants here are of no use to you.")
			)

			((Said '/arch')
				(Print "The arch is impossible to climb.")
			)

			((Said '/flower')
				(Print "The flowers are pretty, but unnecessary.")
			)

			((Said 'jump')
				(Print "It is unnecessary to jump off this cliff.
					Just walk a little closer to the edge.")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	Either entering from 510 while drowning or fell in

			(startDrowning
				(if (!= egoState DROWNING)
					(= egoState DROWNING)
					(soundFX 			stop)
					(music
						number			sNearDanger,
						loop				FOREVER,
						play,
					)
				)
				(HandsOff)
				(ego
					view				vPdrowning,
					setLoop			0,
					setStep			1 3,
					setCycle			Forward,
					setPri			gray,
					illegalBits		0,
				)
				(if (> (ego y) 137)
					(ego
						posn			58 (ego y),
						setMotion	MoveTo 58 137 self,
					)
				else
					(ego
						posn			(- (ego x) 20) (ego y),
					)
					(self cue)
				)
			)

			(drowningAndSplashing
				(if (> (ego y) 100)
					(ego
						setMotion	MoveTo 86 100 self,
					)
				else
					(self cue)
				)
			)

			(splashingAndDrowning
				(ego
					setMotion		MoveTo 105 83 self,
				)
			)

			(atLipOfFalls
				(= egoState NEAR_DEATH)
				(soundFX 			stop)
				(music
					number			sFalling,
					loop				1,
					play,
				)
				(ego
					setPri			green,
					setLoop			2,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(overTheFalls
				(ego
					setStep			FALLING_STEP,
					setMotion		MoveTo (ego x?) 180 self,
				)
			)

			(deadFromWaterfall
				(cls)
				(if debugging
					(NormalEgo)
					(= egoState NORMAL)
					(ego
						posn			178 100
					)
				else
					(theGame setScript (ScriptID DYING))
					((ScriptID DYING) 
						caller		vPdeadWaterfall,
						register 	(Format @string
											"Isn't it difficult to swim upstream... 
											especially when upstream is uphill?"),
						next			(Format @string2 "You've found the FASTEST way down!"),
					)
				)
			)


			;**	Patti gets a little drink of water

			(startDrinking
				(HandsOff)
				(= egoState DRINKING)
				(ego
					view					vPdrinkingStream,
					cycleSpeed			2,
					setLoop				lGoingDown,
					cel					0,
					setCycle				EndLoop self,
				)
			)

			(drinkWater
				(ego
					setLoop				lDrinking,
					cel					0,
					setCycle				Forward,
				)
				;**	Cycle through her drinking loop 4 times:
				(= cycles (* (* 2 (ego cycleSpeed)) (* 4 (- (NumCels ego) 1))))
			)

			(allDone
				(ego
					setLoop				lGoingDown,
					setCel				LastCel,
					setCycle				BegLoop self,
				)
			)

			(tinyDelay
				(delay 3)
			)

			(doneDrinking
				(theGame changeScore 42)
				(SetFlg drankRiverWater)
				(NormalEgo facingLeft)
				(= egoState NORMAL)
				(Print "You find the stream's water remarkably refreshing,
					without a trace of caffeine.")
			)


			;**	fall over the cliff

			(startFalling
				(HandsOff)
				(Print (Format @string "%s" expletive) HIGH #dispose)
				(= egoState FALLING)
				(soundFX 			stop)
				(music
					number			sFalling,
					loop				1,
					play,
				)
				(ego
					view				vPfalling,
					setLoop			lFalling,
					cel				0,
					illegalBits		0,
					ignoreActors,
					setPri			green,
					setCycle			EndLoop self,
				)
			)

			(falling2
				(ego
					setStep			FALLING_STEP,
					setMotion		MoveTo (ego x) 188 self,
				)
				(if debugging
					(= state (- deadFromWaterfall 1))
				)
			)

			(gonnaDieIn525						
				(curRoom newRoom 525)		
			)


			;**	remove her pantyhose

			(tegethoff
				(HandsOff)
				(Ok)
				(theGame changeScore 15)
				(SetFlg pantyhoseOff)
				(ego
					view				vPtyingHose,
					loop				lRemovingHose,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(standBackUp
				(ego
					cel				3,
					setCycle			BegLoop self,
				)
			)

			(timing
				(NormalEgo)
			)


			;**	climb down using pantyhose

			(moveToTitRock
				(HandsOff)
				(Ok)
				(theGame changeScore 40)
				(= egoState PANTYHOSING)
				(ego
					illegalBits		0,
					setMotion		MoveTo tiePosn self,
				)
			)

			(tie2rock
				(ego
					view				vPtyingHose,
					loop				lTieToRock,
					cel				0,
					cycleSpeed		1,
					setPri			ltGreen,
					setCycle			EndLoop self,
				)
			)

			(PolishComedian
				(= cycles 11)
			)

			(tie2self
				(ego
					loop				lTieToSelf,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(youBroughtHer
				(= cycles 11)
			)

			(goOverEdge
				(Print "You remove your pantyhose,
					tie one leg to the tip of the large rock,
					tie the other around your waist,
					then lower yourself carefully over the edge of the cliff."
					#icon iPantyhose 0 0
					HIGH
				)
				(ego
					setLoop			lOverTheSide,
					cel				0,
					posn				rockPosn,
					setCycle			EndLoop self,
				)
			)

			(goDown
				(ego
					setPri			green,
				)
				(= cycles 5)
			)

			(youTimer
				(ego
					setStep			1 1,
					setMotion		MoveTo (ego x) (+ 30 (ego y))self,
				)
			)

			(changeRooms
				(Print "You hope these are \"support\" hose!" HIGH)
				(curRoom newRoom 525)
			)
		)
	)
)

(instance aRock1 of Prop
	(properties
		view					vStream
		loop					lRock1
		x						-18 
		y						160
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock2 of Prop
	(properties
		view					vStream
		loop					lRock2
		cel					1
		x						56 
		y						187
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock3 of Prop
	(properties
		view					vStream
		loop					lRock2
		x						37 
		y						179
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock4 of Prop
	(properties
		view					vStream
		loop					lRock3
		x						-5 
		y						118
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
(instance aRock5 of Prop
	(properties
		view					vStream
		loop					lRock3
		cel					1
		x						12 
		y						176
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			isExtra			TRUE,
			ignoreActors,
		)
	)
)
