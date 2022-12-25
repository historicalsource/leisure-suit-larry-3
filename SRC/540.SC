;**
;**	Logics for room 540 -- Feral Pig
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#				540)

(public
	rm540				0
)


(define	vFeralPig					540)
	(define	lSnorting			0)
	(define	lPreJump				1)
	(define	lAttacking			2)
	(define	lShrinking			3)
	(define	lSplash				4)

(define	vPremovingBra				541)
	(define	lRemovingBra		0)
	(define	lNutsInBra			1)
	(define	lTwirlingBra		2)
	(define	lBra					3)

(define	vPcollapsing				542)

;(define	vPdeadPorked				543)


;(define	sHitBottom					012)
;(define	sLasso						530)
;(define	sFeralPig					540)
(define	sFeralPigJumping			541)
;(define	sSplash						561)


(define	pigControl				cBlue)

(define	makePigLeave		(& (ego onControl origin) cRed))
(define	bringPigOut			(& (ego onControl origin) cGreen))


(enum												;**	RoomScript state values
	intro
	tegethoff
	she'sBralessNow
	putItBackOn
	okItsOnAgain
	insertNuts
	nutsInPlace
	twirlItBaby
	braTossed
	braAtPig
	noGood
	piggyGoneByeBye
	UdidIt
)


(enum												;**	PigScript state values
	PigOffScreen
	PigStartsOut
	PigBlockingPath
	PigRetreats
	PigAttack
	airborne
	PigOnPatti
	PattiDown
	UBdying
	braIsComing
	braAtPiggy
	pigInRiver
	piggyGone
)

(enum 540
	ATTACKING
)


(local
	seenMsg
	[string  33] 
	[string2 22]
)


(instance rm540 of Room
	(properties
		picture		540
		horizon		78
		south			530
		north			550
	)


	(method (init)
		(super init)

		(self
			setScript 		RoomScript
		)

		(if (== prevRoomNum 550)
			(ego
				posn 		233 80,
			)
		else
			(ego
				posn		233 188,
			)
		)

		(if (not (TstFlg killedPorky))
			(Load VIEW vPremovingBra)
			(Load VIEW vPcollapsing)
			(Load VIEW vPdeadPorked)

			(Load SCRIPT JUMP)
			(Load SCRIPT WANDER)
			
			(Load SOUND sFeralPig)	
			(Load SOUND sFeralPigJumping)	
			(Load SOUND sHitBottom)
			(Load SOUND sSplash)
			(Load SOUND sLasso)

			(aPig		 	init)
			(aBra		 	init)
		)

		(= normalEgoView	vPshortDress)
		((Inventory at iDress) view ivDressShort)
		(= egoState NORMAL)
		
		(NormalEgo facingNorth)
		(ego					init)
	)

	(method (newRoom n)
		(super newRoom n)
		(LoadMany FALSE WANDER)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (and	(== state twirlItBaby)
					(== vPremovingBra (ego view))
					(== lTwirlingBra (ego loop))
					(== 1 (ego cel)))
			(soundFX
				number			sLasso,
				loop				1,
				play,
			)								 			
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'use/bra, nut')
				(Print "That's an idea. The question is: \"How?\"")
			)

			((Said 'use/marker')
				(Print "The feral pig is unfamiliar with magic.")
			)

			((and (ego has iKey)
					(Said '/key'))
				(Print "Your key will not help you, Patti.")
			)

			((Said 'climb/tree')
				(Print "No matter which tree you climb it won't do you
					any good!")
			)

			((Said '(put<on), wear/bra')
				(cond
					((InRoom iBra 484)
						(Print "A good idea.
							How you wished you had worn yours
							when you left your apartment!")
					)
					((InRoom iBra LIMBO)
						(DontHave)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (TstFlg braLess))
						(Print "You ARE wearing it!")
					)
					((TstFlg braLoaded)
						(Print "First, get those coconuts out of there!")
					)
					(else
						(self changeState putItBackOn)
					)
				)
			)

			((Said 'remove, (off<take), (take<off)/bra')
				(cond
					((InRoom iBra 484)
						(Print "A good idea.
							How you wished you had worn yours
							when you left your apartment!")
					)
					((InRoom iBra LIMBO)
						(DontHave)
					)
					((TstFlg braLess)
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

			((and (TstFlg braLoaded)
					(Said 'remove, (out<take), (take<out)/nut'))
				(ClrFlg braLoaded)
				(Print "O.K. You remove the coconuts from your brassiere.
					Now what?")
			)

			((Said 'put/nut/bra')
				(cond
					((not (ego has iBra))
						(Print "You're not wearing your bra.")
					)
					((not (ego has iCoconuts))
						(Print "You have no nuts, Patti!")
						(if (>= filthLevel DIRTY)
							(Print "(After all, you are a woman!)" LOW)
						)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (TstFlg braLess))
						(Print "Nah! Sierra made me promise not to do any more
							Dolly Parton jokes!")
						(Print "(Better take it off first!)" LOW)
					)
					((TstFlg braLoaded)
						(Print "You've already loaded that thing enough!")
					)
					(else
						(self changeState insertNuts)
					)
				)
			)

			((or	(Said 'use/bra/sling')
					(Said 'sling/nut/pig')
					(Said 'throw/bra'))
				(cond
					((InRoom iBra 484)
						(Print "A good idea.
							How you wished you had worn yours
							when you left your apartment!")
					)
					((not (ego has iBra))
						(DontHave)
					)
					((not (TstFlg braLess))
						(Print "You would, but you're still in it!")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((!= (PigScript state) PigBlockingPath)
						(Print "Good idea, but there is nothing at which to throw.")
					)
					(else
						(self changeState twirlItBaby)
					)
				)
			)

			((Said 'throw/nut')
				(cond
					((ego has iCoconuts)
						(Print "You are not strong enough to hurl a coconut 
							with enough velocity to stop one of Nontoonyt's 
							feral pigs!")
					)
					(else
						(Print "You have no nuts, Patti!")
						(if (> filthLevel CLEAN)
							(Print "(Grin.)" LOW)
						)
					)
				)
			)

			((Said 'throw>')
				(cond
					((Said '[/noword]')
						(Print "What did you wish to throw?")
					)
					((Said '/anyword[/noword]')
						(Print "At what?")
					)
				)
			)

			((Said 'climb, crawl')
				(Print "There's no way around or under or over this problem.
					You must eliminate that boar!")
			)

			((Said 'look>')
				(cond
					((Said '/river')
						(Print "You feel that the river is somehow a key
							to your success.")
					)

					((Said '[/room]')
						(Print "In the distance a river runs downhill.")
						(Print "(You expected maybe, uphill?)" LOW)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	Patti removes her bra...

			(tegethoff
				(HandsOff)
				(SetFlg braLess)
				(if (not (TstFlg scoredBraLess))
					(SetFlg scoredBraLess)
					(theGame changeScore 5)
				)
				(Ok)
				(ego
					view				vPremovingBra,
					setLoop			lRemovingBra,
					cel				0,
					illegalBits		0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(she'sBralessNow
				(Print "Ahh! That feels better." HIGH)
				(NormalEgo facingNorth)
			)


			;**	Some dumb shit wants to wear it again...

			(putItBackOn
				(HandsOff)
				(Ok)
				(ClrFlg braLess)
				(ClrFlg braLoaded)
				(ego
					view				vPremovingBra,
					setLoop			lRemovingBra,
					setCel			LastCel,
					illegalBits		0,
					cycleSpeed		1,
					setCycle			BegLoop self,
				)
			)

			(okItsOnAgain
				(Print "O.K. It's on. Now what?")
				(NormalEgo facingNorth)
			)


			;**	Stick your nuts in my bra...

			(insertNuts
				(HandsOff)
				(SetFlg braLoaded)
				(if (not (TstFlg scoredBraLoaded))
					(SetFlg scoredBraLoaded)
					(theGame changeScore 45)
				)
				(Ok)
				(ego
					view				vPremovingBra,
					setLoop			lNutsInBra,
					cel				0,
					illegalBits		0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(nutsInPlace
				(Print "Good idea, Patti!
					You are now holding a bra containing a pair of
					heavy coconuts.")
				(NormalEgo facingNorth)
				(Print "(Now what?)" LOW)
			)


			;**	Throw that sucker...

			(twirlItBaby
				(HandsOff)
				(Ok)
				(ClrFlg braLess)
				(music stop)
				(= oldGameSpeed (theGame setSpeed 6))
				(= egoState ATTACKING)
				(ego
					view				vPremovingBra,
					setLoop			lTwirlingBra,
					illegalBits		0,
					cel				0,
					setCycle			Forward,
				)
				(= cycles (* 5 (- (NumCels ego) 1)))
			)

			(braTossed
				(NormalEgo facingNorth)
				(HandsOff)
				(aBra
					ignoreHorizon,
					posn				(+ (ego x) 23) (- (ego y) 43),
					setLoop			lBra,
					setCycle			Forward,
					setStep			8 8,
					setMotion		JumpTo (- (aPig x) 2) (- (aPig y) 13) self,
				)
				(PigScript changeState braIsComing)	;**	stay where you are!
			)

			(braAtPig
				(PigScript 			cue)
				(aBra					dispose)

				(if (TstFlg braLoaded)
					(ego
						put			iBra LIMBO,
						put 			iCoconuts LIMBO,
					)
					(self changeState (- UdidIt 1))
				else
					(delay 3)
				)
			)

			(noGood
				(Print "Unfortunately, Patti, the pig refuses to wear your bra.
					Really! Attacking a pig with just a bra? 
					You must be nuts!")
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadPorked,
					register 	(Format @string
										"That pig has really got you up a tree, Patti."),
					next			(Format @string2 "How boaring..."),
				)
			)


			;**	She did it!!  Wait here until we get a cue from PigScript...

			(piggyGoneByeBye
				(= cycles 0)
				(delay 3)
			)

			(UdidIt
				(Print "You did it, Patti!
					Your 36C bolas has done its duty." HIGH)
				(NormalEgo facingNorth)
				(= egoState NORMAL)
				(Print "(But have you noticed how you're running dangerously
					low on clothing?)" LOW)
				(music	  
					number		sJungleRegion,
					loop			bgMusicLoops,
					play,
				)
				(SetFlg killedPorky)
				(theGame changeScore 100)
				(theGame setSpeed oldGameSpeed)
			)
		)
	)
)


(instance aBra of Actor
	(properties
		view					vPremovingBra
		x						999
		y						999
		illegalBits			0
	)
)

(instance aPig of Actor
	(properties
		view					vFeralPig
		x						288
		y						111
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			setLoop			lSnorting,
			setPri			1,
			setCycle			Forward,
			setStep			4 4,
			setScript		PigScript,
		)
	)
)
(instance PigScript of Script
	(method (doit)
		(super doit)

		(cond
			((== egoState ATTACKING)
				;**	DON'T let pig change!
			)
			((and ( < state PigAttack)
					(& (ego onControl) pigControl))
				(self changeState PigAttack)
			)
			((and makePigLeave
					(or	(== state PigStartsOut)
							(== state PigBlockingPath)))
				(self changeState PigRetreats)
			)
			((and bringPigOut
					(or	(== state PigOffScreen)
							(== state PigRetreats)))
				(self changeState PigStartsOut)
			)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(PigOffScreen
			)

			(PigStartsOut
				(music
					number			sFeralPig,
					loop				FOREVER,
					play,
				)
				(aPig
					setMotion		MoveTo 216 97 self,
				)
			)

			(PigBlockingPath
				(if (not seenMsg)
					(= seenMsg TRUE)
					(Print "Patty, look out!!
						It's one of Nontoonyt Island's rare feral pigs,
						exercising his territorial rights_-- right at you!" BIG)
				)
				(aPig
			 		illegalBits		( & $FFFF ( ~ pigControl)), ;**	Stay on blue.
					setMotion		Wander,
					setPri			RELEASE,
					setStep			2 2,
				)
			)

			(PigRetreats
				(music				fade)
				(aPig
					illegalBits		0,
					setPri			1,
					setMotion		MoveTo 288 111 self,
				)
				(= state readyToStartOver)
			)


			;**	Piggy attack!!!

			(PigAttack
				(= egoState NEAR_DEATH)
				(HandsOff)
				(Print "LOOK OUT!!" HIGH)
				(soundFX				stop)
				(music
					number			sFeralPigJumping,
					loop				1,
					play,
				)
				(aPig
					illegalBits		0,
					ignoreActors,
					setPri			RELEASE,
					setLoop			lPreJump,
					setCel			0,
					setCycle			EndLoop,
					setStep			4 10,
					setMotion		MoveTo (- (aPig x) (/ (- (aPig x) (ego x)) 2)) -10 self,
				)
			)

			(airborne
				(aPig
					setLoop			lAttacking,
					setCycle			Forward,
					setMotion		MoveTo (ego x) (- (ego y) 40) self
				)
			)

			(PigOnPatti
				(music
					number			sHitBottom,
					loop				1,
					play,
				)
				(ego
					view				vPcollapsing,
					cel				0,
					setCycle			EndLoop,
				)
				(aPig
					setMotion		MoveTo (ego x) (ego y) self,
				)
			)

			(PattiDown
				(ego					stopUpd)
				(aPig
					setLoop			lSnorting,
					setCycle			Forward,
				)
				(delay 3)
			)

			(UBdying
				(if (>= filthLevel DIRTY)
					(Print "Once again, Patti, you find yourself being porked!"
						HIGH)
					(Print "...an experience not wholly unfamiliar to you!"
						LOW)
				else
					(Print "From the looks of things, Patti..."
						HIGH)
					(Print "...\"That's All, Folks!\05\""
						HIGH)
				)
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadPorked,
					register 	(Format @string
										"Are you sure Oscar Mayer\05 started like this?"),
					next			(Format @string2 "Busted flat again"),
				)
			)


			;**	Hit by a flying bra!

			(braIsComing
				(= cycles 0)
				(delay 0)
				(aPig
					setMotion		FALSE,
				)
			)

			(braAtPiggy
				(= cycles 0)
				(delay 0)
				(music stop)
				(if (TstFlg braLoaded)
					(soundFX
						number			sHitBottom,
						loop				1,
						play,
					)
					(aPig
						illegalBits		0,
						setLoop			lShrinking,
						cel				0,
						setStep			4 4,
						setCycle			EndLoop,
						setMotion		JumpTo 225 69 self,
					)
				else
					(self changeState PigStartsOut)
				)
			)

			(pigInRiver
				(soundFX
					number			sSplash,
					loop				1,
					play,
				)
				(aPig
					setLoop			lSplash,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(piggyGone
				(RoomScript changeState piggyGoneByeBye)
				(aPig
					setScript		FALSE,
					dispose,
				)
			)
		)
	)

	(method (handleEvent event)
		(cond
			((Said '//pig>')
				(cond
			  		(( > (aPig x) 275)
					 	(Print "Where?")
						(event claimed TRUE)
					)
					((Said 'throw/anyword>')
						(if (Said '/bra')
							(event claimed FALSE)
						else
							(Print "That wouldn't have any effect on this wild boar!")
							(event claimed TRUE)
						)
					)
					(else
						(Print "The feral pig just grunts and snorts,
							laughing at your attempts to pass.")
						(event claimed TRUE)
					)
				)
			)

			((Said '/pig>')
				(cond
			  		(( > (aPig x) 280)
					 	(Print "Where?")
						(event claimed TRUE)
					)

					((Said 'feed/')
						(Print "The only thing he's interested in eating is you,
							Patti!")
					)

					((Said 'hit, cut/')
						(Print "You would love to,
							but you just can't reach the pig from here.")
					)
					
					((Said 'talk')
						(Print "As much as the feral pig would like to carry on
							a conversation.")
					)

					((Said 'look/')
						(Print "A feral pig is successfully preventing you from proceeding.
							He looks mean! You'd better not get too close, Patti.")
					)

					(else
						(Print "The feral pig just grunts and snorts.")
						(event claimed TRUE)
					)
				)
			)
		)
	)
)

