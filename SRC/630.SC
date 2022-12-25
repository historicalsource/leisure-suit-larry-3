;**
;**	Logics for room 630 -- SQ2 weightless in coveralls
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				630)

(public
	rm630				0
)


(define	vRoom							630)
	(define 	lGeneratorBottom	0)
	(define 	lPlug					1)
	(define 	lGeneratorTop		2)

(define	vLroom						631)
	(define 	lTrippingEL			0)
	(define 	lSpinning			1)
	(define 	lHittingFloor		2)
	(define 	lStandingEL			3)
	(define 	lStunned				4)

(define	vProom						632)
;	(define 	lTrippingEL			0)
;	(define 	lSpinning			1)
;	(define 	lFalling630				2)
	;**	Patti should also have these:
;	(define 	lStandingEL			3)
;	(define 	lStunned				4)
 

;(define	sFalling						004)
;(define	sEgoStunned					005)
(define	sSQ2							630)
(define	sGeneratorWindDown		631)
;(define	sSierraRegion				699)


(define	nearSwitch		(ego inRect 190 126 261 154))
(define	fallToY						152)
(define	stunnedY						181)


(enum												;**	RoomScript state values
	intro
	PisSpinning
	PinsideControlLines
	LatEdgeOfScreen
	LisSpinning
	LatTop
	pullPlug
	pauseAlittle
	heyLook
	waitAminute
	PhitFloor
	LhitFloor
	bothStand
	bothStanding
	allOver
)


(enum 630  
	FLOATING
)


(synonyms
	(wire plug )
)


(instance rm630 of Room
	(properties
		picture		630
		horizon		1
		east			640
	)

	
	(method (init)
		(Load VIEW vLhunk)
		(Load VIEW vLroom)
		(Load VIEW normalEgoView)

		(Load SOUND sSQ2)
		(Load SOUND sFalling)
		(Load SOUND sEgoStunned)
		(Load SOUND sGeneratorWindDown)
		(Load SOUND sSierraRegion)

		(Load SCRIPT REVERSE)
		(Load SCRIPT WANDER)

		(super init)

		(aGeneratorBottom		init)
		(aGeneratorTop			init)
		(aSwitch					init)
		(aLarry	  				init)

		(NormalEgo)
		(ego						
			illegalBits		0,
			view				vProom,
			setLoop			lTrippingEL,
			setPri			13,
			cel				0,
			posn				7 183,
			init,
			baseSetter		SQ3Base,
		)


		(self
			setScript 		RoomScript
		)

		(= egoState FLOATING)
		(music
			number			sSQ2,
			loop				bgMusicLoops,
			play,
		)
	)


	(method (newRoom n)
		(DisposeScript WANDER)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
		(if (== egoState FLOATING)
			(aLarry
				brLeft		(- (aLarry x) 4),
				brRight		(+ (aLarry x) 4),
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'plug, attach, (on<turn)/plug, generator, switch')
				(if (== egoState NORMAL)
					(ItIs)
				else
					(Print "Good. Opposite thinking sometimes helps!")
				)
			)

			((Said 'unplug, remove, (off<turn), stop, pull/plug, 
																generator, switch')
				(cond
					((!= egoState FLOATING)
					 	(NotNow)
					) 	
					((not nearSwitch)
						(NotClose)
					)
					((or	( < (ego cel) 6)
							( > (ego cel) 9))
						(Print "You must be upside down in order to reach
							the plug from where you are.")
					)
					(else
			 			(RoomScript changeState pullPlug)
					)
				)
			)	

			((Said 'take/wire, plug')
				(Print "You have no use for any part of the Anti-Gravity
					Generator.")
			)

			((Said 'look>')
				(cond
					((Said '/wire, plug, cable')
						(if (== egoState FLOATING)
							(Print "A small wire attaches the Anti-Gravity
								Generator to an electrical outlet in the
								floor of the set.")
						else
							(Print "It's unplugged now!")
						)
					)

					((Said '/larry')
						(Print "He looks out of this world!")
					)

					((Said '/machine, generator')
						(if (== egoState FLOATING)
							(Print "So that's how those guys do it!
								The \"Acme Anti-Gravity Generator\" has enough
								power to keep both you and Larry afloat!
								You wonder how much electricity is coursing through
								that small wire plugged into the floor of the set.")
						else
							(Print "It's lost all its attraction to you!")
						)
					)

					((Said '[/room]')
						(Print "You are on the set of \"Space Quest.\"")
						(if (== egoState FLOATING)
							(Print "A machine is working away down near the
								floor.")
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(HandsOff)
				(ego
					setMotion		MoveTo 19 171,
					setCycle			EndLoop self,
				)
			)

			(PisSpinning
				(ego
					posn				35 149,
					setLoop			lSpinning,
					cel				1,
					setCycle			Forward,
					cycleSpeed		4,
					moveSpeed		1,
					setStep			1 1,
					setMotion		MoveTo 72 138 self,
				)
			)

			(PinsideControlLines
				(Printf "\"%s\" you shout. \"Now what?\"" expletive)
				(ego
					observeControl	cLtMagenta,
					baseSetter		SQ3Base,
					setMotion		MoveTo 187 99 self,
				)
				(aLarry
					setMotion		MoveTo 6 185 self,
				)
			)
			
			(LatEdgeOfScreen
				(aLarry
					view				vLroom,
					setLoop			lTrippingEL,
					cel				0,
					setMotion		MoveTo 18 173,
					setCycle			EndLoop self,
				)
			)

			(LisSpinning
				(Print "\"We're weightless!!\" Larry yells.")
				(aLarry
					posn				31 150,
					setLoop			lSpinning,
					cel				1,
					setCycle			Forward,
					cycleSpeed		4,
					moveSpeed		1,
					setStep			1 1,
					setMotion		MoveTo 153 41 self,
				)
				(HandsOn)
				(ego
					illegalBits		0,
					observeControl	cLtWhite cLtMagenta,
					baseSetter		SQ3Base,
				)
			)

			(LatTop
				(aLarry
					observeControl	cLtWhite cYellow,
					setMotion		Wander 22 ,
				)
			)


			;**	Pulling Patti's Plug.

			(pullPlug
				(Ok)
				(HandsOff)
				(theGame changeScore 40)
				(aSwitch
					setCycle			EndLoop self,
				)
				(aLarry
					setMotion		MoveTo 148 (aLarry y),
				)
			)

			(pauseAlittle
				(aSwitch				stopUpd)
				(aGeneratorTop
					setCel			0,
					stopUpd,
				)
				(if (== bgMusicLoops FOREVER)	;**	only wait for GOOD sound
					(music
						number			sGeneratorWindDown,
						loop				1,
						play 				self,
					)	
				else
					(= cycles 22)
				)
			)

			(heyLook
				(Print "\"Look, Larry,\" you cry, 
					\"I did it! 
					The anti-gravity generator is unplugged!\"")
				(if (== bgMusicLoops FOREVER)
					(delay 2)
				else								;**	pretty subtle, eh?
					(= cycles 22)
				)
			)

			(waitAminute
				(= egoState FALLING)
				(Print "\"Wait a minute, Patti!\" Larry yells.
					\"If there's no anti-gravity...\"")
				(music
					number			sFalling,
					loop				1,
					play 				,
				)
				(ego
					baseSetter		FALSE,
					illegalBits		0,
					cycleSpeed		0,
					moveSpeed		0,
					setStep			3 4,
					setMotion		MoveTo (ego x) fallToY self,
				)
				(aLarry
					illegalBits		0,
					cycleSpeed		0,
					moveSpeed		0,
					setStep			3 4,
					setMotion		MoveTo (aLarry x) fallToY self,
				)
			)

			(PhitFloor
				(ego
					posn				(ego x) stunnedY,
					loop				lHittingFloor,
					cel				0,
					setCycle			EndLoop ,
				)
			)

			(LhitFloor
				(aLarry
					posn				(aLarry x) stunnedY,
					loop				lHittingFloor,
					cel				0,
					setCycle			EndLoop ,
				)
				(delay 3)
			)

			(bothStand
				(ego
					cycleSpeed		1,
					loop				lStandingEL,
					cel				0,
					setCycle			EndLoop ,
				)
				(aLarry
					cycleSpeed		1,
					loop				lStandingEL,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(bothStanding
				(aLarry
					loop				lStunned,
					setCycle			Forward,
				)
				(ego
					loop				lStunned,
					setCycle			Forward,
				)
				(music
					number			sEgoStunned,
					loop				1,
					play				self,			
				)
			)

			(allOver
				(= egoState NORMAL)
				(NormalEgo facingNorth)
				(NormalActor aLarry facingNorth vLhunk)
				(aLarry
					setMotion		Follow ego 28,
				)
				(music
					number			sSierraRegion,
					loop				bgMusicLoops,
					play,
				)
				(Print "\"Wow, Patti! That was a rough one. What's next?\"")
			)
		)
	)
)


(instance aGeneratorBottom of View
	(properties
		view				vRoom
		loop				lGeneratorBottom
		x					227
		y					149
	)
	(method (init)
		(super init)
		(self
			setPri			5,
			ignoreActors,
			stopUpd,
		)
	)
)
(instance aGeneratorTop of Prop
	(properties
		view				vRoom
		loop				lGeneratorTop
		x	 				235
		y					133
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri			6,
			setCycle			Forward,
		)
	)
)
(instance aSwitch of Prop
	(properties
		view				vRoom
	 	loop				lPlug
		x					213
		y					153
	)
	(method (init)
		(super init)
		(self
			setPri			7,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aLarry of Actor
	(properties
		view 				vLhunk
		x					-28
		y					184
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			setCycle			Forward,
			setPri			5,
			ignoreActors,
		)
	)
)
(instance SQ3Base of Code
	(method (doit &tmp w)
		(ego brBottom		(+ (ego y) 1))
		(ego brTop			(- (ego brBottom) (ego yStep)))
		(ego brLeft			(- (ego x) 18))
		(ego brRight		(+ (ego x) 18))
	)
)

