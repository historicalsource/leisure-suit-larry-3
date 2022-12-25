;**
;**	Logics for room 560 -- Patti riding her big log
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(script#				560)

(public
	rm560				0
)


(define	collisionDistance			9)


(define	vRoom							560)
	(define	lLeftBank			0)
	(define	lHorizon				1)
	(define	lRightBank			2)
	(define	lDot					3)
										
(define	vPattiOnLog					561)
	(define	lOhNo					4)
	(define	lFallOff				5)
	(define	lLogAlone			6)

(define	vObstacles					562)
	(define	lRock					0)
	(define	lLog					1)
	(define	lGator				2)
	(define	lBaby					3)

(define	vPdeadWhitewater			563)


;(define	sWhitewaterRafting		560)
;(define	sFalling						004)	
;(define	sSplash						561)


(define	MAX_POINTS					150)
(define	vanishY						 89)

(define	dotX							258)
(define	dotY							188)


(enum												;**	RoomScript state values
	intro
	moveBanks
	inDanger
	pastObstacle
	youMadeIt
	changeRooms
	tossPatti
	pattiOffHerLog
	extraState
	UBdying
)


(enum												;**	DotScript state values
	DotInit
	level1
	level2
	level3
	level4
	finishedRoom
)


(local
	seenMsg
	startingScore
	OB_counter
	destX
	theCounter
	killX
	killY
	babyState
	lastLoop
	[string  40] 
	[string2 22]
)


(enum 560	 
	INLIMBO
	COMING
	MOVING
	HIT_GATOR
	READY
	DONE
)


(instance rm560 of Room
	(properties
		picture		560
		horizon		-10
	)


	(method (init)
		(Load SOUND sFalling)
		(Load SOUND sSplash)

		(Load SCRIPT DPATH)
		
;**			(SetFlg cantSave)

		(= destX	222)
		(= startingScore score)

		(super 				init)

		(addToPics
			add 				atpHorizon,
			doit,
		)
		
		(aDot					init)
		(aRightBank			init)
		(aLeftBank			init)
		(aObstacle			init)

		(self
			setScript 		RoomScript
		)

		(NormalEgo)
		(ego
			view				vPattiOnLog,
			posn 				222 113,
			setPri			6,
			setStep			3 1,
			setCycle			Forward,
			init,
		)
		(HandsOff)
		(User
			canControl		TRUE,
		)

		(music
			number			sWhitewaterRafting,
			loop				FOREVER,
			priority			9999,
			play,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (and (== state inDanger)
					( < (aObstacle distanceTo ego) collisionDistance))
			(self changeState tossPatti)
		)
	)


	(method (handleEvent event)
		(if (and	(== (event type) keyDown)
					(== (event claimed) FALSE)
					(== (event message) `#8)
					( < state youMadeIt))
			(Print "Skipping ahead...")
			(= score startingScore)
			(SetFlg skippedLogRide)
			(curRoom newRoom 580)
			(return)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(aObstacle
					hide,
				)
				(if (== (DotScript state) finishedRoom)
					(self changeState youMadeIt)
				else
					(= cycles (Random 5 10))
				)
			)

			(moveBanks
				(if (not (Random 0 2))
					(= destX (Random 200 235))
					(aLeftBank
						setMotion		MoveTo destX 92,
					)
					(aRightBank
						setMotion		MoveTo destX 92 self,
					)
				else
					(self cue)
				)
				(if (and (not (Random 0 2))
							(< DotScript level3))
					(= state readyToStartOver)
				)
			)

			(inDanger
				;**	Mostly, aim it at Patti.
				(if ( > (= killX (Random 200 400)) 240)
					(= killX (ego x))
				)
				;**	Fudge X a little since destination Y is below Patti.
				(cond
					((< killX (- destX 5))
						(- killX 5)	
					)
					((> killX (+ destX 5))
						(+ killX 5)	
					)
				)
				(aObstacle
					posn				destX vanishY,
					setLoop 			(Random 0 lastLoop),
					cel				lastLoop,
					setCycle			EndLoop,
					show,
					setMotion		MoveTo killX (+ (ego y) 5) self,
				)
				(if (== (aObstacle loop) lBaby)
					(-- lastLoop)				;**	tie her tubes
					(Print "Lookout, Patti! It's your worst nightmare!" HIGH)
				)
			)

			(pastObstacle
				(if (== (aObstacle loop) lBaby)
					(Print "Children!!" HIGH)
				)
				(theGame changeScore lastLoop)	;**	more points as we progress
				(self changeState intro)
			)


			;**	She went all the way!

			(youMadeIt
				(Print "You made it!" HIGH)
				(theGame changeScore (- MAX_POINTS (- score startingScore)))
;**					(if (TstFlg inQA)
;**						(Printf "Attention: QA-type! You will now get %d bonus points
;**							to bring your score up to this room's maximum score of %d.
;**							(You started with %d and are ending with %d.)
;**							Does this seem fair to you?" 
;**							(- MAX_POINTS (- score startingScore))
;**							MAX_POINTS 
;**							startingScore
;**							(+ score (- MAX_POINTS (- score startingScore)))
;**						)
;**					)
				(theGame changeScore (- MAX_POINTS (- score startingScore)))
				(delay 3)
			)

			(changeRooms
				(Print "But now what lies ahead?" HIGH)
				(DisposeScript DPATH)
				(music
					priority			0,
				)
				(curRoom newRoom 580)
			)


			;**	She fucked up and hit something...

			(tossPatti
				(= cycles 0)
				(delay 0)
				(HandsOff)
				(aLeftBank
					setCel			0,
				)
				(aRightBank
					setCel			0,
				)
				(aDot
					setScript		FALSE,
					setMotion		FALSE,
					setCel			0,
				)
				(aObstacle
					hide,
				)
				(ego
					setLoop			lOhNo,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(pattiOffHerLog
				(music				stop)
				(Print "Oh, no!" HIGH)
				(soundFX
					number			sFalling,
					loop				1,
					play,
				)
				(aLog					init)
				(ego
					cycleSpeed		0,
					illegalBits		0,
					ignoreActors,
					setStep			3 3,
					setLoop			lFallOff,
					cel				0,
					setCycle			EndLoop self,
					setMotion		MoveTo (aLeftBank x) 100 self,
				)
			)

			(extraState
			)

			(UBdying
				(soundFX
					number			sSplash,
					loop				1,
					play,
				)
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller			vPdeadWhitewater,
					register 		(Format @string
											"Don't you just hate arcade games!"),
					next				(Format @string2 "Insert Another Quarter"),
				)
			)
		)
	)
)



(instance DotScript of Script
	(method (changeState newState)		
		(ShowState self newState 2 green)

		(switch (= state newState)

			(DotInit
				(delay 1)
			)

			(level1
				(= lastLoop lRock)
				(aObstacle
					setStep			2 2,
					cycleSpeed		1,
				)
				(aDot
					setMotion		DPath	209 179
												192 183
												176 186
												151 187
												127 186
												116 182
												108 182
												101 179
												self,
				)
			)
			(level2
				(= lastLoop lLog)
				(aObstacle
					setStep			3 3,
					cycleSpeed		0,
				)
				(aDot
					setMotion		DPath	93 178
												78 173
												66 164
												55 150
												51 140
												41 133
												36 126
												36 115
												35 109
												29 104
												31 96
												35 85
												38 78
												self,
				)
			)

			(level3
				(= lastLoop lBaby)
				(aObstacle
					setStep			4 4,
				)
				(aDot
					setMotion 		DPath	35 73
												39 65
												45 59
												48 52
												56 44
												67 34
												78 22
												self,
				)
			)

			(level4
				;**	How Hard shall we make this thing?
;**					(aObstacle
;**						setStep			5 5,
;**					)
				(aDot
					setMotion		DPath	86 15
												100 9
												109 4
												112 -3
												self,
				)
			)

			(finishedRoom
			)
		)
	)
)
(instance aDot of Actor
	(properties
		view					vRoom
		x						dotX
		y						dotY
		moveSpeed			1
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			setScript		DotScript,
			setCycle			Forward,
			setLoop			lDot,
			setStep			1 1,
			setPri			15,
			illegalBits		0,
			ignoreActors,
		)
	)
)

(instance atpHorizon of PicView
	(properties
		view					vRoom
		x						223
		y						91
		loop					lHorizon
		signal				ignrAct
	)
)

(instance aLeftBank of Actor
	(properties
		view					vRoom
		x						222
		y						92
;		moveSpeed			1
	)

	(method (init)
		(super init)
		(self
			setLoop			lLeftBank,
			setCycle			Forward,
			setStep			2 2,
			illegalBits		0,
			ignoreActors,
		)
	)
)
(instance aRightBank of Actor
	(properties
		view					vRoom
		x						222
		y						92
;		moveSpeed			1
	)

	(method (init)
		(super init)
		(self
			setLoop			lRightBank,
			setCycle			Forward,
			setStep			2 2,
			illegalBits		0,
			ignoreActors,
		)
	)
)

(instance aObstacle of Actor
	(properties
		view					vObstacles
		x						222
		y						87
	)

	(method (init)
		(super init)
		(self
			setPri			5,
			illegalBits		0,
			ignoreActors,
		)
	)
)

(instance aLog of View
	(properties
		view				vPattiOnLog
		loop				lLogAlone
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri		5,
			posn			(ego x) (ego y),
		)
	)
)

