;**
;**	Logics for room 416 -- mirrored hallway, East
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				416)

(public
	rm416				0
)


(define	cardX						160)
(define	cardY						 60)
(define	crapX						122)
(define	crapY						 60)
(define	stairsX 					 61)
(define	stairsY 					 61)
(define	rightX					198)
(define	leftX						 72)
(define	walkwayY					 15)
(define	walkerX					132)


(define	vMirrorGamblers				414)
	(define	lPlayingCards			1)		;**	This is the mirror of rm415 loops
	(define	lShootingCraps			3)

(define	vMirrorWalkers1				415)
	;**	v415 also has north/south loops
(define	vMirrorWalkers2				416)
(define	vMirrorWalkers3				417)
(define	vMirrorWalkers4				418)
(define	vMirrorWalkers5				419)


(enum												;**	RoomScript state values
	intro
	demo1
	demo2
	demo3
	demo4
	demo5
)


(instance rm416 of Room
	(properties
		picture		416
		west			410
		east			450
	)


	(method (init)
		(Load VIEW vMirrorWalkers1)
		(Load VIEW vMirrorWalkers2)
		(Load VIEW vMirrorWalkers3)
		(Load VIEW vMirrorWalkers4)
		(Load VIEW vMirrorWalkers5)
		
		(super init)

		(self
			setRegions		regCasinoHallway,
		)

		(cond
			((== prevRoomNum 410)						(ego posn   1 143))
			((or	(== egoState WEARING_ZIEGFIELD)
					( > (ego yLast) 180))				(ego posn 315 136))
			(else												(ego posn 290 122))
		)

		(NormalEgo)
		(ego					init)
		(aCraps				
			init,
			isExtra			TRUE,
		)
		(aCard  
			init,
			isExtra			TRUE,
		)
		(if (> machineSpeed pc8088)
			(aWalker			init)
			(aAlterEgo		init)
		)
	)


	(method (doit)
		(super doit)

		(if (or	(& (ego onControl) cBlue)
					(and	(== egoState WEARING_ZIEGFIELD)
							(& (ego onControl) cGreen)))
			(curRoom newRoom 450)
		)
	)
)

;**	(instance RoomScript of Script
;**		(method (handleEvent event)
;**			(if (and (not (super handleEvent event))
;**						(not (event claimed))
;**						modelessDialog
;**						(== (event message) ENTER)
;**						(== (event type) keyDown))
;**				(event claimed TRUE)
;**				(cls)
;**				(self cue)
;**			)
;**		)
;**	
;**	
;**		(method (changeState newState)
;**			(switch (= state newState)
;**	
;**				(intro
;**					(if demo
;**						(= cycles 6)
;**					)
;**				)
;**	
;**				(demo1
;**					(ego
;**						setMotion		MoveTo 1 159,
;**					)
;**					(= cycles 9)
;**				)
;**	
;**				(demo2
;**					(Print "When you fell in love with Larry Laffer,
;**						you had no idea this Nontoonyt Casino gig
;**						would take you places you've never been before..."
;**						BIG #dispose #time 7)
;**					(delay (+ 2 7))
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(Print "...such as outdoors..."
;**						HIGH #dispose #time 2)
;**					(delay 3)
;**				)
;**	
;**				(demo4
;**					(cls)
;**					(curRoom newRoom 530)
;**				)
;**	
;**				(demo5
;**					(cls)
;**					(curRoom newRoom 530)
;**				)
;**			)
;**		)
;**	)


(instance aAlterEgo of Actor
	(properties
		view					vLarry
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			view				(ego view),
			setPri			red,
			posn				(ego x) 122,
			ignoreHorizon,
			ignoreActors,
		)
	)

	(method (doit)
		(aAlterEgo
			view				(ego view),
			loop				(switch (ego loop)
									(facingNorth	facingSouth)
									(facingSouth	facingNorth)
									(else				(ego loop))
								),
			cel				(ego cel),
			x					(ego x),
			y					(- 125 (/ (- (ego y) 127) 2)),
		)
		(super doit)
	)
)


(instance aCraps of Extra
	(properties
		view 				vMirrorGamblers
      loop				lShootingCraps
		cel				11
		x					crapX
		y					crapY
		cycleSpeed		0
		cycleType		ExtraEndLoop
		hesitation		11
		pauseCel			11
		minPause			22
		maxPause			111
	)
)
(instance aCard of Extra
	(properties
		view 				vMirrorGamblers
      loop				lPlayingCards
		cel				0
		x					cardX
		y					cardY
		cycleType		ExtraForward
		minPause			11
		maxPause			55
		minCycles		15
		maxCycles		44
	)
)

(instance aWalker of Actor
	(properties
		view				vMirrorWalkers1
		x					119
		y					walkwayY
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreActors,
			setCycle			Walk,
			setStep			2 1,
			setScript		WalkerScript,
		)
	)
)
(instance WalkerScript of Script
	(method (changeState newState)		
		(ShowState self newState 1 green)

		(switch (= state newState)

			(0
				(delay (Random 2 6))
			)

			(1
				(switch (Random 0 6)
					(facingRight
						(aWalker
							view				(Random vMirrorWalkers1 vMirrorWalkers5),
							posn				leftX walkwayY,
							setPri			1,
							setMotion		MoveTo rightX walkwayY self,
						)
					)	
					(facingLeft
						(aWalker
							view				(Random vMirrorWalkers1 vMirrorWalkers5),
							posn				rightX walkwayY,
							setPri			1,
							setMotion		MoveTo leftX walkwayY self,
						)
					)	
					(facingSouth
						(aWalker
							view				vMirrorWalkers1,
							posn				walkerX 32,
							setPri			0,
							setMotion		MoveTo walkerX 142 self,
						)
					)	
					(facingNorth
						(aWalker
							view				vMirrorWalkers1,
							posn				walkerX 142,
							setPri			0,
							setMotion		MoveTo walkerX 32 self,
						)
					)	
					(else
						(delay 2)
					)	
				)
				(= state readyToStartOver)
			)
		)
	)
)
