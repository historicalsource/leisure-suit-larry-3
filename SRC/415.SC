;**
;**	Logics for room 415 -- mirrored hallway, West
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				415)

(public
	rm415				0
)


(define	cardX						160)
(define	cardY						 59)
(define	crapX						198)
(define	crapY						 62)
(define	stairsX 					 61)
(define	stairsY 					 61)
(define	rightX					119)
(define	leftX						251)
(define	walkwayY					 15)
(define	walkerX					186)


(define	vMirrorGamblers				414)
	(define	lPlayingCards			0)		;**	each loop is mirrored
	(define	lShootingCraps			2)

(define	vMirrorWalkers1				415)
	;**	v415 also has north/south loops
(define	vMirrorWalkers2				416)
(define	vMirrorWalkers3				417)
(define	vMirrorWalkers4				418)
(define	vMirrorWalkers5				419)


(instance rm415 of Room
	(properties
		picture		415
		east			410
		west			420
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
			((== prevRoomNum 410)						(ego posn 318 143))
			((or	(== egoState WEARING_ZIEGFIELD)
					( > (ego y) 180))						(ego posn   1 143))
			(else												(ego posn  31 122))
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
			(curRoom newRoom 420)
		)
	)
)


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
		view				vMirrorGamblers
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
							posn				walkerX 33,
							setPri			0,
							setMotion		MoveTo walkerX 142 self,
						)
					)	
					(facingNorth
						(aWalker
							view				vMirrorWalkers1,
							posn				walkerX 142,
							setPri			0,
							setMotion		MoveTo walkerX 33 self,
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

