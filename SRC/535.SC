;**
;**	Logics for room 535 -- Hanging around
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 3, 1989
;**


(script#				535)

(public
	rm535				0
)


(define	vTinyBird					535)
(define	vProom						536)
	(define	lFallingSmallest	1)

(define	sPonRope 					535)	
(define	sPonRopeStinger			536)	


(enum												;**	RoomScript state values
	intro
	youFind
	youAreGrowingTired
	kneesTogether
	atMiddle
	madeIt
	allDone
	fallToDeath
	lookOut
	inLimbo
)


(local
	[string  77] 
	[string2 22]
)


(instance rm535 of Room
	(properties
		picture		535
		horizon		1
	)


	(method (init)
		(Load SOUND sFalling)
		(Load SOUND sPonRopeStinger)

		(music
			number		sPonRope,
			loop			FOREVER,
			play,
		)

		(HandsOff)

		(if (TstFlg forceAtest)
			(= normalEgoView vPshortDress)
		)

		(super init)

		(aBird1				init)
		(aBird2				init)
		(aBird3				init)

		(self
			setScript 		RoomScript
		)

		(ego
			view				vProom,
			loop				0,
			cel				0,
			ignoreActors,
			setStep			1 1,
			moveSpeed		1,
			cycleSpeed		1,
			posn				49 26,
			init,
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(intro
				(delay 2)
			)

			(youFind
				(if (== normalEgoView vPshortDress)
					(Print "You feel certain your informal safety harness
						will help you pass safely over this canyon." WIDE)
					(Print "(But are your feelings correct?)" LOW)
				else
					(Print "Well, well, well.
						Isn't this another fine mess you've gotten us into!
						Now you're hanging by your hands and knees
						from a rope woven from marijuana stems
						suspended high above a tremendously deep chasm." WIDE)
				)
				(ego
					setMotion		MoveTo 280 26
				)
				(= cycles (Random 55 111))
			)

			(youAreGrowingTired
				(if (!= normalEgoView vPshortDress)
					(Print "Your hands rapidly tire from holding your
						body's full weight on the rope.")
				)
				(= cycles (Random 55 111))
			)

			(kneesTogether
				(if (!= normalEgoView vPshortDress)
					(if (>= filthLevel DIRTY)
						(Print "You are having trouble keeping your knees together.")
						(Print "...an experience not wholly unfamiliar to you!" LOW)
					else
						(Print "Your legs are becoming fatigued;
							you wonder how long you will be able to continue!")
					)
				else
					(Print "Your improvised safety harness works!
						You feel you may be able to make it all the way across!")
				)
				(= cycles (Random 55 111))
			)

			(atMiddle
				(if (== normalEgoView vPshortDress)
					(ego
						setMotion		MoveTo 280 26 self,
					)
				else
					(self changeState fallToDeath)
				)
			)

			(madeIt
				(music
					number		sPonRopeStinger,
					loop			1,
					play,
				)
				(Print "You did it, Patti!
					But, now you wonder if there was something you forgot
					before you left.")
				(Print "Of course, you could always take the rope back again!")
				(= cycles 33)
			)

			(allDone
				(= egoState NORMAL)
				(curRoom newRoom 530)
			)


			;**	Oops.

			(fallToDeath
				(music
					number			sFalling,
					loop				1,
					play,
				)
				(ego
					moveSpeed			0,
					cycleSpeed			0,
					setLoop				lFallingSmallest,
					setCel				0,
					setCycle				EndLoop self,
				)
			)

			(lookOut
				(Print "LOOK OUT!!" #at 10 5 #dispose)
				(ego
					setMotion			theJump ,
				)
			)

			(inLimbo
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadFalling,
					register 	(Format @string 
										"If only you had some way to secure yourself 
										to the rope.
										Your hands have certain skills, 
										but rope grasping is not one of them!"),
					next			(Format @string2 "Remember Christopher Reeves"),
				)
			)
		)
	)
)


(instance aBird1 of Actor
	(properties
		view					vTinyBird
		x						13
		y						19
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setScript		Bird1Script,
			setCycle			Walk,
		)
	)

)
(instance Bird1Script of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(if (not (Random 0 2))
					(delay (Random 3 7))
				)
			)

			(1
				(aBird1
					setMotion			MoveTo 333 19 self,
				)
			)
		)
	)
)

(instance aBird2 of Actor
	(properties
		view					vTinyBird
		x						274
		y						138
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setScript		Bird2Script,
			setCycle			Walk,
		)
	)
)
(instance Bird2Script of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(if (not (Random 0 3))
					(delay (Random 1 3))
				)
			)

			(1
				(aBird2
					setMotion			MoveTo -14 138 self,
				)
			)
		)
	)
)

(instance aBird3 of Actor
	(properties
		view					vTinyBird
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			posn				(Random 70 234) 23,
			setScript		Bird3Script,
			setCycle			Walk,
		)
	)
)
(instance Bird3Script of Script
	(method (doit)
		(super doit)

		(if (and (== state 0)
					( > 11 (client distanceTo ego)))
			(self cue)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(1
				(aBird3
					setMotion			MoveTo 335 155 self,
				)
			)
		)
	)
)
(instance theJump of Jump
	(method (init)
		(super init ego RoomScript)
		(self
			yStep 	5,
			y 			300,
		)
	)
)

