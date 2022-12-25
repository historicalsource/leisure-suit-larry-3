;**
;**	Logics for region 41 -- Falling script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 3, 1989
;**


(script#				41)

(public
	rm41				0
)


(local
	fallPri
	destY
	restoreX
	restoreY
	[string 	44] 
	[string2 22]
)

(enum												;**	FallScript state values
	intro
	startFalling
	falling2
	fallDone
)


(instance rm41 of Region
	(method (init)
		(Load SOUND sFalling)

		(if egoIsPatti
			(Load VIEW vPfalling)
		else
			(Load VIEW vLfalling)
		)

		(super init)

		(self
			setScript		FallScript,
		)
	)

	(method (notify fallingPriority destinationY)
		(= fallPri fallingPriority)
		(= destY destinationY)
		(FallScript changeState startFalling)
	)
)

(instance FallScript of Script
	(method (doit)
		(if (and debugging
					(== egoState NORMAL))
			(= restoreX (ego x))
			(= restoreY (ego y))
		)

		(super doit)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(startFalling
				(HandsOff)
				(soundFX
					number			sFalling,
					loop				1,
					play,
				)
				(Print (Format @string "%s" expletive) HIGH #dispose)
				(= egoState FALLING)
				(ego
					view				(if egoIsPatti vPfalling
										else				vLfalling),
					setLoop			lFalling,
					cel				0,
					illegalBits		0,
					ignoreActors,
					setPri			fallPri,
					setCycle			EndLoop self,
				)
			)

			(falling2
				(ego
					setMotion		theJump
				)
			)

			(fallDone
				(cls)
				(if debugging
					(NormalEgo)
					(= egoState NORMAL)
					(ego
						posn 			restoreX restoreY,
					)
				else
					(theGame setScript (ScriptID DYING))
					((ScriptID DYING) 
						caller		(+ 1 (ego view)), ;** falling dead = falling+1
						register 	(Format @string
											"Well, %s, are you now fully convinced 
											that gravity really sucks?" egoName),
						next			(Format @string2 "You really FELL for that one...")
					)
				)
			)
		)
	)
)
(instance theJump of Jump
	(method (init)
		(super init ego FallScript)
		(self
			yStep 	5,
			y 			destY,
		)
	)
)

