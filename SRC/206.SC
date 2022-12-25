;**
;**	Logics for room 206 -- Peep show
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#				206)

(public
	rm206				0
)


(define vRoom							206)
	(define	lShade				0)
	(define	lWallLeft			1)
	(define	lWallCenter			2)
	(define	lWallRight			3)

(define vStripper						207)
	(define	lWalkingR			0)
	(define	lWalkingL			1)
	(define	lWalkingNakedR		2)
	(define	lWalkingNakedL		3)
	(define	lTurningR2F			4)
	(define	lLoweringShade		5)
	(define	lRemovingBra		6)
	(define	lRemovingJeans		7)

(define	vSeaGull						208)
	(define	lFlyingEast		0)
	(define	lFlyingWest		1)
	(define	lTakeOff			2)


;(define	sVoyeur						206)

(define	GIRL_Y						109)


(enum												;**	RoomScript state values
	intro
	moveToSwitch
	cueTheBand
	turnOffLights
	moveTo1
	turnFront1
	raiseArm1
	lowerShade1
	turnRight1
	moveTo2
	turnFront2
	raiseArm2
	lowerShade2
	turnRight2
	moveTo3
	turnFront3
	raiseArm3
	lowerShade3
	turnRight3
	moveToStrip
	removeBra
	braOff
	removePants
	stripDone
	stripperLeaving
	stripperGone
	leaveRoom
)


(enum
	NOTHERE
	ENTERING
	TURNINGOFFLIGHTS
	SHADINGROOM
	STRIPPING
	LEAVING
	ALLDONE
)


(local
	seenMsg
	stripperState
)


(instance rm206 of Room
	(properties
		picture		206
	)

	(method (init)
		(HandsOff)
		(User canInput TRUE)
		(SetFlg noCursor)
		(= oldEgoState egoState)
		(= egoState curRoomNum)
		(Load SOUND sVoyeur)

		(super init)

		(self setScript	RoomScript)

		(aShade1				init)
		(aShade2				init)
		(aShade3				init)
		(aGull				init)
		(aGirl				init)
		(= oldGameSpeed (theGame setSpeed 6))
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'look/room')
					(Said 'look<stop, away')
					(Said 'stop/look')
					(Said 'look<stop/plaque')
					(Said 'leave, stop')
		 			(Said 'put/binocular'))
				(Ok)
				(= egoState oldEgoState)
			 	(curRoom newRoom 200)
			)

			((Said 'listen/girl')
				(Print "These are binoculars, not microphones!")
			)

			((Said 'talk/girl')
				(Print "She can't hear you.")
			)

			((Said '/panties, pants')
				(Print "Only you and Dennis Jonathan would stare at those
					tiny little things!")
			)

			((Said 'move/binocular')
				(Print "You attempt to turn the binoculars so as not to see
					into that woman's room, but you just can not!")
				(Print "(Hee, hee!)" LOW)
			)

			((Said 'look<in/binocular')
				(Print "This is as far in as you can look!")
			)

			((Said 'look/room')
				(Print "You are voyeuring into a woman's room in the hotel.")
				(Print "(No wonder this is called a scenic viewpoint!)" LOW)
			)

			((Said 'look[/girl]')
				(switch stripperState
					(ENTERING
						(Printf "%s! You're looking into a woman's hotel room!"
							egoName)
					)
					(TURNINGOFFLIGHTS
						(Print "She's turned off the lights!")
					)
					(SHADINGROOM
						(Print "She's pulling down the shades.")
					)
					(STRIPPING
						(Print "Oh, my gawd! She's taking off her clothes!")
					)
					(LEAVING
						(Print "WOW!")
					)
					(ALLDONE
						(Print "Did you see that?!")
					)
					(else
						(Print "Where?")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(= stripperState ENTERING)
				(delay 3)
			)

			(moveToSwitch
				(aGirl
					setMotion		MoveTo 10 GIRL_Y self,
				)
				(= cycles 11)
			)

			(cueTheBand
				(music
					number			sVoyeur,
					loop				FOREVER,
					play,
				)
			)

			(turnOffLights
				(addToPics						;**	Add dark wallpaper
					add 				atpWall1,
					add 				atpWall2,
					add 				atpWall3,
					doit
				)
				(= cycles 3)
				(= stripperState TURNINGOFFLIGHTS)
			)

			(moveTo1
				(aGirl
;					setLoop			lWalkingR,
					setCycle			Walk,
					setMotion		MoveTo 111 GIRL_Y self,
				)
			)

			(turnFront1
				(aGirl
					setLoop			lTurningR2F,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(raiseArm1
				(= stripperState	SHADINGROOM)
				(aGirl
					setLoop			lLoweringShade,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(lowerShade1
				(aGirl
					setCycle			BegLoop,
					cycleSpeed		3,
				)
				(aShade1
					setMotion		MoveTo 113 (- 102 (* 8 filthLevel)) self,
				)
			)

			(turnRight1
				(aShade1				stopUpd)
				(aGirl
					setLoop			lTurningR2F,
					setCel			LastCel,
					setCycle			BegLoop self,
					cycleSpeed		1,
				)
			)

			(moveTo2
				(aGirl
					setLoop			lWalkingR,
					setCycle			Walk,
					setMotion		MoveTo 162 GIRL_Y self,
					cycleSpeed		0,
				)
			)

			(turnFront2
				(aGirl
					setLoop			lTurningR2F,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(raiseArm2
				(aGirl
					setLoop			lLoweringShade,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(lowerShade2
				(aGirl
					setCycle			BegLoop,
					cycleSpeed		3,
				)
				(aShade2
					setMotion		MoveTo 169 (- 102 (* 8 filthLevel)) self,
				)
			)

			(turnRight2
				(aShade2				stopUpd)
				(aGirl
					setLoop			lTurningR2F,
					setCel			LastCel,
					setCycle			BegLoop self,
					cycleSpeed		1,
				)
			)

			(moveTo3
				(aGirl
					setLoop			lWalkingR,
					setCycle			Walk,
					setMotion		MoveTo 220 GIRL_Y self,
					cycleSpeed		0,
				)
			)

			(turnFront3
				(aGirl
					setLoop			lTurningR2F,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(raiseArm3
				(aGirl
					setLoop			lLoweringShade,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(lowerShade3
				(aGirl
					setCycle			BegLoop,
					cycleSpeed		3,
				)
				(aShade3
					setMotion		MoveTo 225 (- 102 (* 8 filthLevel)) self,
				)
			)

			(turnRight3
				(aShade3				stopUpd)
				(= cycles 22)
			)

			(moveToStrip
				(aGirl
					setLoop			lWalkingL,
					setCycle			Walk,
					setMotion		MoveTo 169 GIRL_Y self,
					cycleSpeed		0,
				)
			)

			(removeBra
				(= stripperState	STRIPPING)
				(aGirl
					setLoop			lRemovingBra,
					setCel			0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(braOff
				(= cycles (+ 8 filthLevel))
			)

			(removePants
				(aGirl
					setLoop			lRemovingJeans,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(stripDone
				(= cycles (+ 8 filthLevel))
			)

			(stripperLeaving
				(= stripperState	LEAVING)
				(music				fade)
				(aGirl
					setLoop			lWalkingNakedR,
					setCycle			Walk,
					setMotion		MoveTo 300 GIRL_Y self,
					cycleSpeed		0,
				)
			)

			(stripperGone
				(= stripperState	ALLDONE)
				(delay 3)
			)

			(leaveRoom
				(SetFlg beenIn206)
				(theGame 
					changeScore 	2,
					setSpeed 		oldGameSpeed,
				)
				(Print "You wipe the sweat from your hands as you remove
					your eyes from the binoculars.")
				(= egoState oldEgoState)
			 	(curRoom newRoom 200)
			)
		)
	)
)


(instance aShade1 of Actor
	(properties
		view				vRoom
		x					113
		y					64
		illegalBits		0
	)
	(method (init)
		(super init)
		(self
			setLoop		lShade ,
			setPri		11,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aShade2 of Actor
	(properties
		view				vRoom
		x					169
		y					64
		illegalBits		0
	)
	(method (init)
		(super init)
		(self
			setLoop		lShade ,
			setPri		11,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aShade3 of Actor
	(properties
		view				vRoom
		x					225
		y					64
		illegalBits		0
	)
	(method (init)
		(super init)
		(self
			setLoop		lShade ,
			setPri		11,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance atpWall1 of PicView
	(properties
		view				vRoom
		loop				lWallLeft
		x					113
		y					109
		illegalBits		0
	)
)

(instance atpWall2 of PicView
	(properties
		view				vRoom
		loop				lWallCenter
		x					169
		y					109
		illegalBits		0
	)
)

(instance atpWall3 of PicView
	(properties
		view				vRoom
		loop				lWallRight
		x					225
		y					109
		illegalBits		0
	)
)

(instance aGirl of Actor
	(properties
		view 				vStripper
		x					263
		y					GIRL_Y
		illegalBits		0
	)
	(method (init)
		(super init)
		(self
;			setLoop		lWalkingL,
			setCycle		Walk,
			ignoreActors,
		)
	)
)

(instance aGull of Actor
	(properties
		view 				vSeaGull
		x					-66
		y					70
		illegalBits		0
	)
	(method (init)
		(super init)
		(self
			setPri		14,
			setCycle		Forward,
			ignoreActors,
			setMotion	MoveTo 319 44,
		)
	)
)

